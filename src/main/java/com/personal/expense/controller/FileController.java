package com.personal.expense.controller;

import com.personal.expense.model.UploadedFile;
import com.personal.expense.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    // UPLOAD DIRECTORY

    private final String UPLOAD_DIR =
            System.getProperty("user.dir")
                    + File.separator
                    + "uploads"
                    + File.separator;

    // OPEN UPLOAD PAGE

    @GetMapping("/upload")
    public String uploadPage(Model model) {

        model.addAttribute(
                "file",
                new UploadedFile()
        );

        return "upload";
    }

    // UPLOAD FILE

    @PostMapping("/upload-file")
    public String uploadFile(
            @RequestParam("file")
            MultipartFile file)
            throws IOException {

        // GENERATE SAFE FILE NAME

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename()
                        .replaceAll("\\s+", "_");

        // CREATE uploads FOLDER

        File directory =
                new File(UPLOAD_DIR);

        if (!directory.exists()) {

            directory.mkdirs();
        }

        // SAVE FILE

        File saveFile =
                new File(
                        UPLOAD_DIR + fileName
                );

        file.transferTo(saveFile);

        // SAVE FILE DETAILS IN DATABASE

        UploadedFile uploadedFile =
                new UploadedFile();

        uploadedFile.setFileName(fileName);

        uploadedFile.setFilePath(fileName);

        fileService.saveFile(uploadedFile);

        return "redirect:/files";
    }

    // SHOW ALL FILES

    @GetMapping("/files")
    public String filesPage(Model model) {

        model.addAttribute(
                "files",
                fileService.getAllFiles()
        );

        return "files";
    }

    // OPEN FILE

    @GetMapping("/view-file/{name}")
    @ResponseBody
    public ResponseEntity<Resource> viewFile(
            @PathVariable String name)
            throws IOException {

        File file =
                new File(
                        UPLOAD_DIR + name
                );

        // CHECK FILE EXISTS

        if (!file.exists()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        // LOAD FILE

        Resource resource =
                new UrlResource(
                        file.toURI()
                                .toURL()
                );

        // DETECT FILE TYPE

        String fileName =
                file.getName()
                        .toLowerCase();

        MediaType mediaType;

        // IMAGE TYPES

        if (fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")) {

            mediaType =
                    MediaType.IMAGE_JPEG;

        } else if (fileName.endsWith(".png")) {

            mediaType =
                    MediaType.IMAGE_PNG;

        } else if (fileName.endsWith(".gif")) {

            mediaType =
                    MediaType.IMAGE_GIF;

        }

        // PDF

        else if (fileName.endsWith(".pdf")) {

            mediaType =
                    MediaType.APPLICATION_PDF;
        }

        // DEFAULT

        else {

            mediaType =
                    MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    // DELETE FILE

    @GetMapping("/delete-file/{name}")
    public String deleteFile(
            @PathVariable String name) {

        File file =
                new File(
                        UPLOAD_DIR + name
                );

        // DELETE PHYSICAL FILE

        if (file.exists()) {

            file.delete();
        }

        // DELETE DATABASE RECORD

        fileService.deleteFile(name);

        return "redirect:/files";
    }
}
package com.personal.expense.controller;

import com.personal.expense.model.UploadedFile;
import com.personal.expense.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final String UPLOAD_DIR =
            System.getProperty("user.dir")
                    + "/uploads/";

    @GetMapping("/upload")
    public String uploadPage(Model model) {

        model.addAttribute(
                "file",
                new UploadedFile()
        );

        return "upload";
    }

    @PostMapping("/upload-file")
    public String uploadFile(
            @RequestParam("file")
            MultipartFile file)
            throws IOException {

        String fileName =
                file.getOriginalFilename();

        File uploadPath =
                new File(UPLOAD_DIR);

        if (!uploadPath.exists()) {

            uploadPath.mkdirs();
        }

        file.transferTo(
                new File(
                        UPLOAD_DIR + fileName
                )
        );

        UploadedFile uploadedFile =
                new UploadedFile();

        uploadedFile.setFileName(fileName);

        uploadedFile.setFilePath(
                "/uploads/" + fileName
        );


        fileService.saveFile(uploadedFile);

        return "redirect:/files";
    }
    @GetMapping("/files")
    public String viewFiles(Model model) {

        model.addAttribute(
                "files",
                fileService.getAllFiles()
        );

        return "files";
    }
}
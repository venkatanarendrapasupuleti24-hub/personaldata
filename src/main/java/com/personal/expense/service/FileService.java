package com.personal.expense.service;

import com.personal.expense.model.UploadedFile;
import com.personal.expense.repository.UploadedFileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    // SAVE FILE

    public void saveFile(
            UploadedFile file) {

        uploadedFileRepository.save(file);
    }

    // GET ALL FILES

    public List<UploadedFile>
    getAllFiles() {

        return uploadedFileRepository.findAll();
    }
}
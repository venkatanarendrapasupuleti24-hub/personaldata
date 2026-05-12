package com.personal.expense.repository;

import com.personal.expense.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    void deleteByFileName(
            String fileName);
}
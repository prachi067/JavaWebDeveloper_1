package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

    @Controller
    @RequestMapping("/file")
    public class FileController {

        private FileService fileService;

        public FileController(FileService fileService) {
            this.fileService = fileService;
        }

        @PostMapping("/upload")
        public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication) throws IOException {
            String error = null;

            if (!fileService.isFilenameAvailable(file.getOriginalFilename())) {
                error = "File with same name already exists.";
            }
            if (error == null) {
                int rowsAdded = fileService.addFile(file, authentication);
                if (rowsAdded < 0) {
                    error = "There was an error uploading File. Please try again.";
                }
            }

            if (error == null) {
                model.addAttribute("success", true);
            } else {
                model.addAttribute("error", error);
            }
            model.addAttribute("activeTab", "files");
            return "result";
        }


        @GetMapping("/delete/{fileName}")
        public String handleFileDelete(@PathVariable String fileName, Model model) {
            fileService.deleteFile(fileName);
            model.addAttribute("success", true);
            model.addAttribute("activeTab", "files");
            return "result";
        }

        @GetMapping("/download/{fileName}")
        public ResponseEntity handleFileDownload(@PathVariable String fileName) {
            Files file = fileService.downloadFile(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(new ByteArrayResource(file.getFileData()));

        }

    }

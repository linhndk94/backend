package com.framework.backend.controller.rest_controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File uploadFile = new File("upload");
        FileUtils.forceMkdir(uploadFile);
        uploadFile = new File(uploadFile.getPath() + "/" + file.getOriginalFilename());
        if (uploadFile.createNewFile()) {
            FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
        }
        return ResponseEntity.ok("File is uploaded");
    }
}

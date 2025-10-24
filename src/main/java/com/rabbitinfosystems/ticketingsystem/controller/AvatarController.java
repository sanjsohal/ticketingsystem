package com.rabbitinfosystems.ticketingsystem.controller;

import com.rabbitinfosystems.ticketingsystem.service.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AttachmentService attachmentService;

    public AvatarController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file,
                                        @RequestPart("firebaseUserId") String firebaseUserId) {
        try {
            attachmentService.uploadAvatar(file, firebaseUserId);
            String url = attachmentService.getAvatarUrl(firebaseUserId);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar");
        }
    }

    @GetMapping("/photo-url/{firebaseUserId}")
    public ResponseEntity<String> getAvatarUrl(@PathVariable String firebaseUserId) {
        String url = attachmentService.getAvatarUrl(firebaseUserId);
        return ResponseEntity.ok(url);
    }

}

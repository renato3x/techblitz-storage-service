package br.com.techblitz.storage.controller;

import br.com.techblitz.storage.dto.FileUploadResponseDTO;
import br.com.techblitz.storage.service.AvatarStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatars")
public class AvatarStorageController {
  private final AvatarStorageService storageService;
  
  public AvatarStorageController(AvatarStorageService storageService) {
    this.storageService = storageService;
  }
  
  @PostMapping("{username}")
  public ResponseEntity<FileUploadResponseDTO> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable String username) {
    var response = this.storageService.upload(file, username);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}

package br.com.techblitz.storage.controller;

import br.com.techblitz.storage.dto.FileUploadResponseDTO;
import br.com.techblitz.storage.service.AvatarStorageService;
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
  public FileUploadResponseDTO uploadAvatar(@PathVariable String username, @RequestParam("file") MultipartFile file) {
    return this.storageService.upload(file, username);
  }
}

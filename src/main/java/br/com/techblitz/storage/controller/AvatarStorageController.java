package br.com.techblitz.storage.controller;

import br.com.techblitz.storage.dto.Response;
import br.com.techblitz.storage.service.storage.AvatarStorageService;
import br.com.techblitz.storage.storagemanager.StorageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatars")
public class AvatarStorageController {
  private final AvatarStorageService avatarStorageService;
  
  public AvatarStorageController(AvatarStorageService avatarStorageService) {
    this.avatarStorageService = avatarStorageService;
  }
  
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Response<StorageMetadata> upload(@RequestParam("file") MultipartFile file) {
    StorageMetadata metadata = this.avatarStorageService.upload(file);
    return new Response<>(HttpStatus.CREATED.value(), metadata);
  }
}

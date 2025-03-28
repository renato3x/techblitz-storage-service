package br.com.techblitz.storage.controller;

import br.com.techblitz.storage.dto.Response;
import br.com.techblitz.storage.service.storage.AvatarStorageService;
import br.com.techblitz.storage.storagemanager.StorageFile;
import br.com.techblitz.storage.storagemanager.StorageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/avatars")
public class AvatarStorageController {
  private final AvatarStorageService avatarStorageService;
  
  public AvatarStorageController(AvatarStorageService avatarStorageService) {
    this.avatarStorageService = avatarStorageService;
  }
  
  @GetMapping("{filename}")
  public ResponseEntity<byte[]> get(@PathVariable String filename) {
    StorageFile file = this.avatarStorageService.get(filename);
    MediaType contentType = MediaType.parseMediaType(file.getContentType());
    return ResponseEntity.ok().contentType(contentType).body(file.getBytes());
  }
  
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Response<StorageMetadata> upload(@RequestParam("file") MultipartFile file) {
    StorageMetadata metadata = this.avatarStorageService.upload(file);
    return new Response<>(HttpStatus.CREATED.value(), metadata);
  }
}

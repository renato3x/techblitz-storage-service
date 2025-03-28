package br.com.techblitz.storage.controller;

import br.com.techblitz.storage.dto.response.FileUploadResponse;
import br.com.techblitz.storage.dto.response.Response;
import br.com.techblitz.storage.service.AvatarStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
  @ResponseStatus(HttpStatus.CREATED)
  public Response<FileUploadResponse> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable String username) {
    var response = this.storageService.upload(file, username);
    return new Response<>(HttpStatus.CREATED.value(), response);
  }
  
  @GetMapping("{username}/{filename}")
  public ResponseEntity<byte[]> getAvatar(@PathVariable String username, @PathVariable String filename) {
    var download = storageService.download(username, filename);

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf(download.contentType()));

    return new ResponseEntity<>(download.bytes(), headers, HttpStatus.OK);
  }
}

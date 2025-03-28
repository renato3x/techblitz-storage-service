package br.com.techblitz.storage.dto.response;

public record FileUploadResponse(String filename, String contentType, String url, Long size) {}

package br.com.techblitz.storage.storagemanager;

public record Download(byte[] bytes, String filename, String contentType) {}

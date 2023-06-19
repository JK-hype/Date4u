package de.materna.date4u.core.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemService {  // https://tiny.one/2p8whad4
    private final Path root =
            Paths.get(System.getProperty("user.home")).resolve("fs");

    public FileSystemService() {
        try {
            if (!Files.isDirectory(root)) Files.createDirectory(root);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public byte[] load(String filename) {
        try {
            return Files.readAllBytes(root.resolve(filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void store(String filename, byte[] bytes) {
        try {
            Files.write(root.resolve(filename), bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
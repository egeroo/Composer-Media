package com.egeroo.composer.core.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.egeroo.composer.core.error.CoreException;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public boolean store(MultipartFile file, String name) {
        String filename = StringUtils.cleanPath(name);
        try {
            if (file.isEmpty()) {
                throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, 
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
        }
        catch (IOException e) {
            throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file " + filename + ": " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read stored files: " + e.getMessage());
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, 
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read file: " + filename + ": " + e.getMessage());
        }
    }
    
    @Override
    public void delete(String filename) {
    	try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
            	 FileSystemUtils.deleteRecursively(file);
            }
    	} catch (IOException e) {
            throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read file: " + filename + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not initialize storage: " + e.getMessage());
        }
    }
}
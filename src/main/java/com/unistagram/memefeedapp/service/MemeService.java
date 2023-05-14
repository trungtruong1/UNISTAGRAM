package com.unistagram.memefeedapp.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.unistagram.memefeedapp.model.Meme;

public interface MemeService {
    
    public String save(String title, MultipartFile file, String author) throws IOException;

    public Optional<Meme> getMemeById(String Id);
}

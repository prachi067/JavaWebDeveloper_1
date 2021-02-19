package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FilesMapper filesMapper;
    private final UserMapper userMapper;

    public FileService(FilesMapper filesMapper, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.filesMapper = filesMapper;
    }

    public boolean isFilenameAvailable(String filename) {
        return filesMapper.getFile(filename) == null;
    }

    public int addFile(MultipartFile file, Authentication authentication) throws IOException {
        User user = userMapper.getUser(authentication.getName());
        return filesMapper.insert(new Files(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), user.getUserId(), file.getBytes() ));
    }

    public List<Files> getUserFiles(Integer userId) {
        return filesMapper.getUserFiles(userId);
    }

    public Files getFile(String filename) {
        return filesMapper.getFile(filename);
    }

    public void deleteFile(String fileName) {
        filesMapper.delete(fileName);
    }

    public Files downloadFile(String fileName) {
        return filesMapper.getFile(fileName);
    }
}

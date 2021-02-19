package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getUserCredentials(Integer userId) {
        return credentialsMapper.getUserCredentials(userId);
    }

    public int addCredentials(Credentials credentials, Authentication authentication) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
        User user = userMapper.getUser(authentication.getName());
        return credentialsMapper.insert(new Credentials(null, credentials.getUrl(), credentials.getUserName(), key, encryptedPassword, user.getUserId()));
    }

    public void deleteCredentials(Integer credentialsId) {
        credentialsMapper.delete(credentialsId);
    }

    public int updateCredentials(Credentials credentials) {
        String key = credentials.getKey();
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
        return credentialsMapper.update(credentials.getCredentialsId(), credentials.getUrl(), credentials.getUserName(), encryptedPassword);
    }

}

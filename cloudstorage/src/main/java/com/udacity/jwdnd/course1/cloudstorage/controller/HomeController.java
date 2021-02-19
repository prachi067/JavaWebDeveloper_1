package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private CredentialsService credentialsService;
    private NotesService notesService;
    private UserService userService;
    private FileService fileService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NotesService notesService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("notes")Notes notes, @ModelAttribute("credentials") Credentials credentials, Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("filelist",fileService.getUserFiles(user.getUserId()));
        model.addAttribute("noteslist",notesService.getUserNotes(user.getUserId()));
        model.addAttribute("credentialslist",credentialsService.getUserCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
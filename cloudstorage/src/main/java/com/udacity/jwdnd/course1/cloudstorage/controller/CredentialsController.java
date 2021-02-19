package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping("/save")
    public String handleCredentialsSave(@ModelAttribute("credentials") Credentials credentials, Model model, Authentication authentication) throws IOException {
        String error = null;
        int rowsAdded;

        if(credentials.getCredentialsId() == 0) {
            rowsAdded = credentialsService.addCredentials(credentials, authentication);
            if (rowsAdded < 0) {
                error = "There was an error adding credentials. Please try again.";
            }
        } else {
            rowsAdded = credentialsService.updateCredentials(credentials);
            if (rowsAdded < 0) {
                error = "There was an error updating credentials. Please try again.";
            }
        }

        if (error == null) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", error);
        }

        model.addAttribute("activeTab", "credentials");
        return "result";
    }


    @GetMapping("/delete/{credentialsId}")
    public String handleCredentialsDelete(@PathVariable Integer credentialsId, Model model) {
        credentialsService.deleteCredentials(credentialsId);
        model.addAttribute("success", true);
        model.addAttribute("activeTab", "credentials");
        return "result";
    }
}

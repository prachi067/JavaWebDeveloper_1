package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/save")
    public String handleNotesSave(@ModelAttribute("notes") Notes notes, Model model, Authentication authentication) throws IOException {
        String error = null;
        int rowsAdded;

        if(notes.getNoteId() == 0) {
            rowsAdded = notesService.addNotes(notes, authentication);
            if (rowsAdded < 0) {
                error = "There was an error adding notes. Please try again.";
            }
        } else {
            rowsAdded = notesService.updateNotes(notes);
            if (rowsAdded < 0) {
                error = "There was an error updating notes. Please try again.";
            }
        }

        if (error == null) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", error);
        }

        model.addAttribute("activeTab", "notes");
        return "result";
    }


    @GetMapping("/delete/{noteId}")
    public String handleNotesDelete(@PathVariable Integer noteId, Model model) {
        notesService.deleteNotes(noteId);
        model.addAttribute("success", true);
        model.addAttribute("activeTab", "notes");
        return "result";
    }
}

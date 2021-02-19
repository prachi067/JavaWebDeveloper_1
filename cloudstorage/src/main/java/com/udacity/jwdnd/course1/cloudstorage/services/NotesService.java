package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NotesService {

    private final NotesMapper notesMapper;
    private final UserMapper userMapper;

    public NotesService(NotesMapper notesMapper, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.notesMapper = notesMapper;
    }

    public int addNotes(Notes notes, Authentication authentication) {
        User user = userMapper.getUser(authentication.getName());
        return notesMapper.insert(new Notes(null, notes.getNoteTitle(), notes.getNoteDescription(), user.getUserId()));
    }

    public List<Notes> getUserNotes(Integer userId) {
        return notesMapper.getUserNotes(userId);
    }

    public void deleteNotes(Integer noteId) {
        notesMapper.delete(noteId);
    }

    public int updateNotes(Notes notes) {
        return notesMapper.update(notes.getNoteId(), notes.getNoteTitle(), notes.getNoteDescription());
    }
}

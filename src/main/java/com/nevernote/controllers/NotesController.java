package com.nevernote.controllers;


import com.nevernote.dto.NoteCreateRequest;
import com.nevernote.dto.NoteUpdateRequest;
import com.nevernote.dto.NoteActionResponse;
import com.nevernote.dto.NoteMiscRequest;
import com.nevernote.entity.Note;
import com.nevernote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    NoteService noteService;

    @GetMapping
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/create")
    public List<NoteActionResponse> newNote(@RequestBody @Valid List<NoteCreateRequest> noteCreateRequest) {
        return noteService.create(noteCreateRequest);
    }

    @PostMapping("/get")
    public List<Note> get(@RequestBody @Valid NoteMiscRequest noteMiscRequest) {
        return noteService.get(noteMiscRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody @Valid NoteMiscRequest noteMiscRequest){
        noteService.delete(noteMiscRequest);
    }

    @PostMapping("/update")
    public List<NoteActionResponse> update(@RequestBody @Valid  List<NoteUpdateRequest> noteUpdateRequest){
        return noteService.update(noteUpdateRequest);
    }
}

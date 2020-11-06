package com.nevernote.controllers;

import com.nevernote.dto.*;
import com.nevernote.entity.Notebook;
import com.nevernote.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notebooks")
public class NotebooksController {

    @Autowired
    NotebookService notebookService;


    @PostMapping("/create")
    public NoteActionResponse newNotebook(@RequestBody @Valid NotebookCreateRequest notebookCreateRequest) {
            return notebookService.create(notebookCreateRequest);
    }

    @PostMapping("/get")
    public List<NotebookView> get(@RequestBody @Valid NotebookGetRequest notebookGetRequest) {
        return notebookService.get(notebookGetRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody @Valid NotebookDeleteRequest notebookDeleteRequest){
        notebookService.delete(notebookDeleteRequest);
    }

    @GetMapping("/getAll")
    public List<Notebook> getAll(){
        return notebookService.getAll();
    }



}

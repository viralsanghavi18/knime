package com.nevernote.controllers;

import com.nevernote.dto.NoteActionResponse;
import com.nevernote.dto.NotebookCreateRequest;
import com.nevernote.util.AppConstants;

public class TestHelper {

    public NoteActionResponse sampleNoteActionResponse(){
        NoteActionResponse noteActionResponse = new NoteActionResponse();
        noteActionResponse.setStatus(AppConstants.REQUEST_SUCCESSFUL);
        noteActionResponse.setTitle(sampleNotebookCreateRequest().getTitle());
        noteActionResponse.setId("test id");
        return noteActionResponse;
    }

    public NotebookCreateRequest sampleNotebookCreateRequest(){
        NotebookCreateRequest notebookCreateRequest = new NotebookCreateRequest();
        notebookCreateRequest.setTitle("test notebook");
        return notebookCreateRequest;
    }
}

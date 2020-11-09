package com.nevernote.data;

import com.google.common.collect.Sets;
import com.nevernote.dto.*;
import com.nevernote.entity.Notebook;
import com.nevernote.util.AppConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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


    public NotebookGetRequest sampleNotebookGetRequest(){
        NotebookGetRequest notebookGetRequest = new NotebookGetRequest();
        notebookGetRequest.setTag("test tag");
        notebookGetRequest.setNotebooks(Arrays.asList("test notebook id"));
        return notebookGetRequest;
    }

    public List<NotebookView> sampleNotebookView(){
        NotebookView notebookView = new NotebookView();
        notebookView.setId(" test notebook view");
        notebookView.setTitle(" test notebook title");
        return Arrays.asList(notebookView);
    }

    public Notebook sampelNotebook(){
        Notebook notebook = new Notebook();
        notebook.setId(" test notebook id");
        notebook.setTitle(" test notebook title");
        notebook.setNoteIDs(Sets.newHashSet("1","2"));
        notebook.setCreatedDate(new Date());
        notebook.setLastModifiedDate(new Date());
        return notebook;
    }

    public NotebookDeleteRequest sampleNotebookDeleteRequest(){

        NotebookDeleteRequest notebookDeleteRequest = new NotebookDeleteRequest();
        notebookDeleteRequest.setNotebooks(Arrays.asList("123"));
        return notebookDeleteRequest;
    }
}

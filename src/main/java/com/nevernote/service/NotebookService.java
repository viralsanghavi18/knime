package com.nevernote.service;

import com.nevernote.dto.*;
import com.nevernote.entity.Notebook;

import java.util.List;

public interface NotebookService {

    public NoteActionResponse create(NotebookCreateRequest notebookCreateRequest);
    public List<NotebookView> get(NotebookGetRequest notebookGetRequest);
    public void delete(NotebookDeleteRequest notebookDeleteRequest);
    public List<Notebook> getAll();
    public void deleteNotebookTags( List<String> tags, String notebookID, String noteID);
    public void updateNotebookLastModified(String notebookID);


}

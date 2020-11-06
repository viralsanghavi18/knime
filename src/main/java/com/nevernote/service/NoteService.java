package com.nevernote.service;

import com.nevernote.dto.NoteActionResponse;
import com.nevernote.dto.NoteCreateRequest;
import com.nevernote.dto.NoteMiscRequest;
import com.nevernote.dto.NoteUpdateRequest;
import com.nevernote.entity.Note;

import java.util.List;

public interface NoteService {

    public List<NoteActionResponse>  create(List<NoteCreateRequest> noteCreateRequest);
    public List<Note> get(NoteMiscRequest noteMiscRequest);
    public void delete(NoteMiscRequest noteMiscRequest);
    public List<NoteActionResponse> update(List<NoteUpdateRequest> noteUpdateRequest);
}

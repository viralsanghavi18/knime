package com.nevernote.controllers;

import com.nevernote.dto.NoteActionResponse;
import com.nevernote.dto.NoteCreateRequest;
import com.nevernote.dto.NoteMiscRequest;
import com.nevernote.dto.NoteUpdateRequest;
import com.nevernote.entity.Note;
import com.nevernote.service.NoteServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotesControllerTest {

    @Spy
    @InjectMocks
    private NotesController notesController;

    @Mock
    private NoteServiceImpl noteService;

    TestHelper testHelper = new TestHelper();

    @Test
    public void newNoteSuccessTest() {
        List<NoteCreateRequest> noteCreateRequestList = sampleNoteCreateRequestList();
        List<NoteActionResponse> noteActionResponseList = Collections.singletonList(testHelper.sampleNoteActionResponse());
        when(noteService.create(noteCreateRequestList)).thenReturn(noteActionResponseList);
        assertEquals(noteActionResponseList, notesController.newNote(noteCreateRequestList));
    }

    @Test
    public void getNoteSuccessTest(){
        NoteMiscRequest noteMiscRequest = sampleNoteMiscRequest();
        List<Note> notes = sampleNotes();
        when(noteService.get(noteMiscRequest)).thenReturn(notes);
        assertEquals(notes, notesController.get(noteMiscRequest));

    }

    @Test
    public void updateNoteSuccessTest(){
        List<NoteUpdateRequest> noteUpdateRequestList = sampleNoteUpdateRequestList();
        List<NoteActionResponse> noteActionResponseList = Collections.singletonList(testHelper.sampleNoteActionResponse());
        when(noteService.update(noteUpdateRequestList)).thenReturn(noteActionResponseList);
        assertEquals(noteActionResponseList, notesController.update(noteUpdateRequestList));

    }

    private List<NoteCreateRequest> sampleNoteCreateRequestList(){
        NoteCreateRequest noteCreateRequest = new NoteCreateRequest();
        noteCreateRequest.setTitle("sample note");
        noteCreateRequest.setBody("sample note body");
        noteCreateRequest.setTags(Arrays.asList("hello","world"));
        return Collections.singletonList(noteCreateRequest);
    }

    private List<Note> sampleNotes() {
        Note note = new Note();
        note.setId("sample note id");
        note.setTags(Arrays.asList("hello","world"));
        note.setTitle("sample note");
        note.setBody("sample note body");
        return Collections.singletonList(note);
    }

    private NoteMiscRequest sampleNoteMiscRequest(){
        NoteMiscRequest noteMiscRequest = new NoteMiscRequest();
        noteMiscRequest.setNoteIDs(Arrays.asList("abc","123"));
        return noteMiscRequest;
    }

    private  List<NoteUpdateRequest> sampleNoteUpdateRequestList(){
        NoteUpdateRequest noteUpdateRequest = new NoteUpdateRequest();
        noteUpdateRequest.setTags(Arrays.asList("hello","world"));
        noteUpdateRequest.setTitle("sample note");
        noteUpdateRequest.setBody("sample note body");
        return Collections.singletonList(noteUpdateRequest);
    }
}

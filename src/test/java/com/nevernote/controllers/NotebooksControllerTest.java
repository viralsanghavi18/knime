package com.nevernote.controllers;


import com.nevernote.data.TestHelper;
import com.nevernote.dto.NoteActionResponse;
import com.nevernote.dto.NotebookCreateRequest;
import com.nevernote.dto.NotebookGetRequest;
import com.nevernote.dto.NotebookView;
import com.nevernote.service.NotebookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotebooksControllerTest {

    @Spy
    @InjectMocks
    private NotebooksController notebooksController;

    @Mock
    private NotebookServiceImpl notebookService;


    private TestHelper testHelper = new TestHelper();

    @Test
    public void newNotebookSuccessTest() {
        NotebookCreateRequest notebookCreateRequest = testHelper.sampleNotebookCreateRequest();
        NoteActionResponse noteActionResponse = testHelper.sampleNoteActionResponse();
        when(notebookService.create(notebookCreateRequest)).thenReturn(noteActionResponse);
        assertEquals(noteActionResponse, notebooksController.newNotebook(notebookCreateRequest));
    }

    @Test
    public void getNotebookSuccessTest() {
        NotebookGetRequest notebookGetRequest = testHelper.sampleNotebookGetRequest();
        List<NotebookView> notebookViewList = testHelper.sampleNotebookView();
        when(notebookService.get(notebookGetRequest)).thenReturn(notebookViewList);
        assertEquals(notebookViewList, notebooksController.get(notebookGetRequest));
    }


}

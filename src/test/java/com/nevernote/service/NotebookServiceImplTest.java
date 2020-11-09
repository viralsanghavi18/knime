package com.nevernote.service;

import com.nevernote.data.TestHelper;
import com.nevernote.dto.*;
import com.nevernote.entity.Notebook;
import com.nevernote.repository.NotebookRepository;
import com.nevernote.util.AppConstants;
import com.nevernote.util.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class NotebookServiceImplTest {

    @Spy
    @InjectMocks
    private NotebookServiceImpl notebookService;

    @Mock
    private NotebookRepository notebookRepository;

    @Mock
    private NoteService noteService;

    @Mock
    CommonUtils commonUtils;



    TestHelper testHelper = new TestHelper();


    @Test
    public void createSuccess(){

        NotebookCreateRequest notebookCreateRequest = testHelper.sampleNotebookCreateRequest();
        NoteActionResponse expectedNoteActionResponse = testHelper.sampleNoteActionResponse();
        NoteActionResponse actualNoteActionResponse = notebookService.create(notebookCreateRequest);

        assertEquals(expectedNoteActionResponse.getTitle(), actualNoteActionResponse.getTitle());
        assertNotNull(actualNoteActionResponse.getId());
        assertEquals(AppConstants.REQUEST_SUCCESSFUL,actualNoteActionResponse.getStatus());
        assertNull(actualNoteActionResponse.getError());

    }

    @Test
    public void getSuccessWithANotebook(){
        NotebookGetRequest notebookGetRequest = testHelper.sampleNotebookGetRequest();
        Notebook expectedNotebook = testHelper.sampelNotebook();
        Mockito.when(notebookRepository.findById(notebookGetRequest.getNotebooks().get(0))).thenReturn(expectedNotebook);
        List<NotebookView> actualNotebookViewList = notebookService.get(notebookGetRequest);

        assertNotNull(actualNotebookViewList);
        assertEquals(expectedNotebook.getTitle(),actualNotebookViewList.get(0).getTitle());
        assertEquals(expectedNotebook.getId(),actualNotebookViewList.get(0).getId());
    }

    @Test
    public void getNoteNotFetched(){
        NotebookGetRequest notebookGetRequest = testHelper.sampleNotebookGetRequest();
        List<NotebookView> actualNotebookViewList = notebookService.get(notebookGetRequest);

        assertNotNull(actualNotebookViewList);
        assertEquals(0,actualNotebookViewList.size());
    }

    @Test
    public void getNoteEmptyRequest(){
        NotebookGetRequest notebookGetRequest = testHelper.sampleNotebookGetRequest();
        notebookGetRequest.setNotebooks(null);
        List<NotebookView> actualNotebookViewList = notebookService.get(notebookGetRequest);

        assertNotNull(actualNotebookViewList);
        assertEquals(0,actualNotebookViewList.size());
    }

    @Test
    public void getAllSuccess() {
        List<Notebook> expectedNotebookList = Arrays.asList(testHelper.sampelNotebook());
        Mockito.when(notebookRepository.findAll()).thenReturn(expectedNotebookList);
        assertEquals(expectedNotebookList,notebookService.getAll());
    }


}

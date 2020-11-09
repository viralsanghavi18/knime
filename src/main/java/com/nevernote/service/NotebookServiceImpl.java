package com.nevernote.service;

import com.nevernote.dto.*;
import com.nevernote.entity.Note;
import com.nevernote.entity.Notebook;
import com.nevernote.entity.NotebookTags;
import com.nevernote.util.AppConstants;
import com.nevernote.util.CommonUtils;
import lombok.extern.log4j.Log4j2;
import com.nevernote.repository.NoteRepository;
import com.nevernote.repository.NotebookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class NotebookServiceImpl implements NotebookService{

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    NoteService noteService;

    @Override
    public NoteActionResponse create(NotebookCreateRequest notebookCreateRequest) {

        // create notebook
        log.info("creating notebook, title: " + notebookCreateRequest.getTitle());
        Notebook notebook = new Notebook();
        notebook.setTitle(notebookCreateRequest.getTitle());
        notebook.setId(UUID.randomUUID().toString());
        Date date = new Date();
        notebook.setCreatedDate(date);
        notebook.setLastModifiedDate(date);
        notebookRepository.save(notebook);

        // form a response
        NoteActionResponse createResponse = new NoteActionResponse();
        BeanUtils.copyProperties(notebook,createResponse);
        createResponse.setStatus(AppConstants.REQUEST_SUCCESSFUL);

        log.info("notebook created with id: " + notebook.getId());

        return createResponse;
    }

    @Override
    public List<NotebookView> get(NotebookGetRequest notebookGetRequest) {

        log.info("get notebooks, requested ids: " + notebookGetRequest.getNotebooks());

        List<NotebookView> result = new ArrayList<>();
        if(notebookGetRequest.getNotebooks()!=null) {
            for (String notebookID : notebookGetRequest.getNotebooks()) {
                Notebook notebook = notebookRepository.findById(notebookID);
                if (notebook != null)
                    result.add(createNotebookView(notebook, notebookGetRequest.getTag()));
            }
        }

        log.info("fetched notebooks successfully");

        return result;
    }

    @Override
    public void delete(NotebookDeleteRequest notebookDeleteRequest) {

        log.info("delete notebooks, requested ids: " + notebookDeleteRequest.getNotebooks());

        for (String notebookID : notebookDeleteRequest.getNotebooks()) {
            Notebook notebook = notebookRepository.findById(notebookID);

            //Delete all notes in the notebook
            NoteMiscRequest noteMiscRequest = new NoteMiscRequest();
            noteMiscRequest.setNoteIDs(List.copyOf(notebook.getNoteIDs()));
            noteService.delete(noteMiscRequest);

            //Delete tag map from the cache
            deleteNotebookAllTags(notebookID);

            //Delete the notebook
            notebookRepository.delete(notebookID);
        }

        log.info("deleted notebooks successfully");

    }

    @Override
    public List<Notebook> getAll() {
        return notebookRepository.findAll();
    }

    private List<Note> readAllNotes(Notebook notebook){
        log.info("reading all notes for notebook id: " + notebook.getId());

        List<Note> notes = new ArrayList<>();
        if(notebook.getNoteIDs()!=null && notebook.getNoteIDs().size()>0){
            for(String noteID: notebook.getNoteIDs()) {
                Note note = noteRepository.findById(noteID);
                if(note!=null) {
                    note.setBody(commonUtils.readFile(note.getId()));
                    notes.add(note);
                }
            }
        }

        log.info("reading notebooks completed");

        return notes;
    }

    private List<Note> readNotesWithTags(Notebook notebook, String tag){

        log.info("reading all notes for notebook id: " + notebook.getId() + " and tag: " + tag);

        List<Note> notes = new ArrayList<>();
        NotebookTags notebookTags = notebookRepository.findNotesByTag(notebook.getId()+tag);

        //Fetch notes having the tag
        if(notebookTags!=null && notebookTags.getNotesWithTag()!=null && notebookTags.getNotesWithTag().size()!=0){
            for(String noteID:notebookTags.getNotesWithTag()){
                Note note = noteRepository.findById(noteID);
                if(note!=null) {
                    note.setBody(commonUtils.readFile(note.getId()));
                    notes.add(note);
                }
            }
        }
        log.info("readNotesWithTags completed successfully");
        return notes;
    }

    private NotebookView createNotebookView(Notebook notebook, String tag){

        log.info("creating notebook view for " + notebook.getId() + " and tag: " + tag);

        NotebookView notebookview = new NotebookView();
        BeanUtils.copyProperties(notebook, notebookview);

        // filter notes based on tag
        if(!StringUtils.isEmpty(tag))
            notebookview.setNotes(readNotesWithTags(notebook,tag));
        else  // if no tag specified, fetch all notes
            notebookview.setNotes(readAllNotes(notebook));

        log.info("notebook view is created successfully");
        return notebookview;

    }

    private void deleteNotebookAllTags(String notebookID){

        log.info("deleting all tags related to notebook " + notebookID);

        //TODO retrieving all objects at once with keys is not scalable. A possible solution is to use scanner for batched reads
        for (String key : notebookRepository.getKeysByPattern(AppConstants.NOTEBOOK_TAGS_PREFIX+notebookID+"*")) {
            notebookRepository.deleteNotebookTags(key);
        }
        log.info("tags deleted successfully ");
    }

    @Override
    public void deleteNotebookTags(List<String> tags, String notebookID, String noteID){

        log.info("deleting tags related to notebook " + notebookID + " note " + noteID + " tags " + tags );
        for (String tag : tags) {
            NotebookTags notebookTags = notebookRepository.findNotesByTag(notebookID+tag);
            notebookTags.getNotesWithTag().remove(noteID);
            notebookRepository.saveNotebookTags(notebookTags);
        }
        log.info("tags deleted successfully ");
    }

    public void updateNotebookLastModified(String notebookID){

        //Update notebook last edited timestamp
        Notebook notebook = notebookRepository.findById(notebookID);
        notebook.setLastModifiedDate(new Date());
        notebookRepository.save(notebook);
    }


}

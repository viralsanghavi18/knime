package com.nevernote.service;

import com.google.common.collect.Sets;
import com.nevernote.dto.NoteActionResponse;
import com.nevernote.dto.NoteCreateRequest;
import com.nevernote.dto.NoteUpdateRequest;
import com.nevernote.entity.Note;
import com.nevernote.entity.Notebook;
import com.nevernote.entity.NotebookTags;
import com.nevernote.util.AppConstants;
import com.nevernote.util.CommonUtils;
import jodd.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import com.nevernote.dto.NoteMiscRequest;
import com.nevernote.repository.NoteRepository;
import com.nevernote.repository.NotebookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

@Service
@Log4j2
public class NoteServiceImpl implements  NoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    NotebookService notebookService;


    @Override
    public List<NoteActionResponse> create(List<NoteCreateRequest> noteCreateRequestList) {

        log.info("Creating " + noteCreateRequestList.size() + " notes");

        List<NoteActionResponse> response = new ArrayList<>();
        for(NoteCreateRequest noteCreateRequest: noteCreateRequestList) {

            NoteActionResponse createResponse = new NoteActionResponse();
            Notebook notebook = notebookRepository.findById(noteCreateRequest.getNotebookID());
            if(notebook == null) {
                createResponse.setStatus(AppConstants.REQUEST_FAILED);
                createResponse.setError(String.format(AppConstants.NOTEBOOK_NOT_FOUND,noteCreateRequest.getNotebookID()));
            }else {
                //save note
                Note note = createNoteFromRequest(noteCreateRequest);
                commonUtils.writeNoteFile(note);
                noteRepository.save(note);

                //update notebook
                if (notebook.getNoteIDs() == null)
                    notebook.setNoteIDs(new HashSet<>());
                notebook.getNoteIDs().add(note.getId());
                notebook.setLastModifiedDate(new Date());
                notebookRepository.save(notebook);

                //update notebook tags
                updateNotebookTags(note.getTags(), note.getNotebookID(), note.getId());

                //form a response
                BeanUtils.copyProperties(note, createResponse);
                createResponse.setStatus(AppConstants.REQUEST_SUCCESSFUL);
            }
                response.add(createResponse);
        }

        log.info("notes created successfully");

        return response;
    }

    @Override
    public List<Note> get(NoteMiscRequest noteMiscRequest) {

        log.info("fetching notes " + noteMiscRequest.getNoteIDs());
        List<Note> response = new ArrayList<>();
        for(String noteID: noteMiscRequest.getNoteIDs()){
            Note note = noteRepository.findById(noteID);
            if(note!=null)
                response.add(note);
        }
        log.info("notes fetched successfully ");

        return response;
    }

    @Override
    public void delete(NoteMiscRequest noteMiscRequest) {
        log.info("deleting notes " + noteMiscRequest.getNoteIDs());
        for(String noteID: noteMiscRequest.getNoteIDs()) {
            Note note = noteRepository.findById(noteID);
            if (note != null){

                //Update notebook references
                Notebook notebook = notebookRepository.findById(note.getNotebookID());
                notebook.getNoteIDs().remove(note.getId());
                notebookRepository.save(notebook);

                //update tags references
                if(note.getTags()!=null && note.getTags().size()!=0){
                    for(String tag: note.getTags()){
                        NotebookTags notebookTags = notebookRepository.findNotesByTag(note.getNotebookID()+tag);
                        if(notebookTags == null)
                            notebookTags = new NotebookTags();
                        notebookTags.getNotesWithTag().remove(note.getId());
                        notebookRepository.saveNotebookTags(notebookTags);
                    }
                }

                //Delete the note
                noteRepository.delete(noteID);

                // update notebook last modified timestamp
                notebookService.updateNotebookLastModified(note.getNotebookID());
            }

        }
        log.info("notes deleted successfully ");

    }

    @Override
    public List<NoteActionResponse> update(List<NoteUpdateRequest> noteUpdateRequestList) {
        log.info("updating " + noteUpdateRequestList.size() + " notes");

        List<NoteActionResponse> noteActionResponseList = new ArrayList<>();
        for(NoteUpdateRequest noteUpdateRequest: noteUpdateRequestList){

            NoteActionResponse noteActionResponse = new NoteActionResponse();
            noteActionResponse.setId(noteUpdateRequest.getNoteID());

            Note note = noteRepository.findById(noteUpdateRequest.getNoteID());

            if(note == null){
                noteActionResponse.setError(String.format(AppConstants.NOTE_NOT_FOUND,noteUpdateRequest.getNoteID()));
                continue;
            }

            if(!StringUtil.isEmpty(noteUpdateRequest.getBody())){
                note.setBody(noteUpdateRequest.getBody());
                commonUtils.writeNoteFile(note);
            }

            if(!StringUtil.isEmpty(noteUpdateRequest.getTitle()))
                note.setTitle(noteUpdateRequest.getTitle());

            if(noteUpdateRequest.getTags()!=null && noteUpdateRequest.getTags().size()!=0) {
                notebookService.deleteNotebookTags(new ArrayList<>(CollectionUtils.removeAll(note.getTags(), noteUpdateRequest.getTags())),note.getNotebookID(), note.getId());
                updateNotebookTags(new ArrayList<>(CollectionUtils.removeAll(noteUpdateRequest.getTags(), note.getTags())),note.getNotebookID(), note.getId());
                note.setTags(noteUpdateRequest.getTags());
            }

            note.setLastModifiedDate(new Date());
            noteRepository.save(note);

            // update notebook last modified timestamp
            notebookService.updateNotebookLastModified(noteUpdateRequest.getNotebookID());

            noteActionResponse.setStatus(AppConstants.REQUEST_SUCCESSFUL);
            noteActionResponseList.add(noteActionResponse);

        }

        log.info("notes updated successfully");
        return noteActionResponseList;
    }

    private Note createNoteFromRequest(NoteCreateRequest noteCreateRequest){
        Note note = new Note();
        BeanUtils.copyProperties(noteCreateRequest,note);
        Date date = new Date();
        note.setId(UUID.randomUUID().toString());
        note.setCreatedDate(date);
        note.setLastModifiedDate(date);
        return note;
    }

    private void updateNotebookTags(List<String> tags, String notebookID, String noteID){
        if(tags!=null && tags.size()!=0){
            for(String tag: tags){
                NotebookTags notebookTags = notebookRepository.findNotesByTag( notebookID + tag);

                // this tag is not present in the notebook
                if (notebookTags == null) {
                    notebookTags = new NotebookTags();
                    notebookTags.setTag(tag);
                    notebookTags.setNotebookID(notebookID);
                    notebookTags.setNotesWithTag(Sets.newHashSet(noteID));
                }
                else // tag is present in some notes
                    notebookTags.getNotesWithTag().add(noteID);

                notebookRepository.saveNotebookTags(notebookTags);
            }
        }
    }
}

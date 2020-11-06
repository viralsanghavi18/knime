package com.nevernote.dto;

import lombok.Getter;
import lombok.Setter;
import com.nevernote.entity.Note;

import java.util.*;

@Setter
@Getter
public class NotebookView extends NotebookCreateRequest {

    private String id;
    private List<Note> notes = new ArrayList<>();
    private Date lastModifiedDate;
    private Date createdDate;
}

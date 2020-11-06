package com.nevernote.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class NotebookTags implements Serializable {

    private static final long serialVersionUID = 1L;

    private String notebookID;
    private String tag;
    private Set<String> notesWithTag;

    public void NotebookTags(){
        notesWithTag = new HashSet<>();
    }


}

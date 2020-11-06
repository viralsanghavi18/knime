package com.nevernote.entity;

import com.nevernote.dto.NoteCreateRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Note extends NoteCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Date lastModifiedDate;
    private Date createdDate;

}

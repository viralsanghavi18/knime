package com.nevernote.entity;

import com.nevernote.dto.NotebookCreateRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class Notebook extends NotebookCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Set<String> noteIDs;
    private Date lastModifiedDate;
    private Date createdDate;


}

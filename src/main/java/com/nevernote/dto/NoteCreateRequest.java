package com.nevernote.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class NoteCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotBlank(message = "notebooks id not present")
    private String notebookID;

    @NotBlank(message = "title can't be empty")
    private String title;

    private String body;

    private List<String> tags;

    public NoteCreateRequest() {
    }

}

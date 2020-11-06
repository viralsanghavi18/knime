package com.nevernote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class NoteUpdateRequest {

    @NotBlank(message = "notebooks id not present")
    private String notebookID;

    @NotBlank(message = "note id is not present")
    private String noteID;
    private String title;
    private String body;
    private List<String> tags;
}

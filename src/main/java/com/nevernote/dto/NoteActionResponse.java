package com.nevernote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteActionResponse {

    private String title;
    private String id;
    private String status;
    private String error;

}

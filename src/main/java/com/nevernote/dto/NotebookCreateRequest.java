package com.nevernote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class NotebookCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "title is mandatory")
    private String title;

}

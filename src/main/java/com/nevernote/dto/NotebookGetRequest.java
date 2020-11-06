package com.nevernote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class NotebookGetRequest {


    @NotNull(message = "notebooks list can't be empty")
    private List<String> notebooks;
    private String tag;
}

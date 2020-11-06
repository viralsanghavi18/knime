package com.nevernote.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class NoteMiscRequest {

    @NotNull(message = "noteIDs list can't be empty")
    public List<String> noteIDs;
}

package com.nevernote.util;

import com.nevernote.entity.Note;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Log4j2
public class CommonUtils {

    @Value("${notes.storage.path}")
    private String path;

    public Boolean writeNoteFile(Note note){
        String filePath = path + note.getId();
        File file = new File(filePath);
        try {
            FileUtils.writeStringToFile(file, note.getBody());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: add logger and exception handling code
            log.error("could not write file " + note.getId() + " exception " + e);
            return false;
        }
        return true;
    }

    public String readFile(String id){
        String filePath = path + id;
        File file = new File(filePath);
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("could not read file " + id + " exception " + e);
            //TODO: add logger and exception handling code
            return null;
        }
    }
}

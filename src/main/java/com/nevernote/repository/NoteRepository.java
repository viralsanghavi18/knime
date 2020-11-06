package com.nevernote.repository;


import com.nevernote.entity.Note;
import com.nevernote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class NoteRepository {

    @Autowired
    private RedisTemplate<String, Note> noteRedisTemplate;

    public void save(Note note) {

        this.noteRedisTemplate.opsForValue()
            .set(AppConstants.NOTE_PREFIX+note.getId(), note);
    }

    public Note findById(String id) {
        return noteRedisTemplate.opsForValue()
            .get(AppConstants.NOTE_PREFIX+id);
    }

    public void delete(String id){
        noteRedisTemplate.delete(AppConstants.NOTE_PREFIX+id);
    }



}

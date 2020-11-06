package com.nevernote.repository;


import com.nevernote.entity.Notebook;
import com.nevernote.entity.NotebookTags;
import com.nevernote.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class NotebookRepository {

    @Autowired
    private RedisTemplate<String, Notebook> redisNotebookTemplate;

    @Autowired
    private RedisTemplate<String, NotebookTags> redisNotebookTagsTemplate;

    public void save(Notebook notebook) {
        redisNotebookTemplate.opsForValue()
            .set(AppConstants.NOTEBOOK_PREFIX+notebook.getId(), notebook);
    }


    public Notebook findById(String id) {
        Notebook result = redisNotebookTemplate.opsForValue()
            .get(AppConstants.NOTEBOOK_PREFIX+id);
        return result;
    }

    public List<Notebook> findAll(){
        List<Notebook> allNotebooks = new ArrayList<>();
        Set<String> keys = redisNotebookTemplate.keys(AppConstants.NOTEBOOK_PREFIX+"*");
        for (String notebookID : keys) {
            allNotebooks.add(redisNotebookTemplate.opsForValue()
                    .get(notebookID));
        }
        return allNotebooks;
    }

    public void delete(String id){
         redisNotebookTemplate.delete(AppConstants.NOTEBOOK_PREFIX+id);

    }

    public void saveNotebookTags(NotebookTags notebookTags) {
        redisNotebookTagsTemplate.opsForValue()
                .set(AppConstants.NOTEBOOK_TAGS_PREFIX+notebookTags.getNotebookID()+notebookTags.getTag(), notebookTags);
    }

    public NotebookTags findNotesByTag(String id) {
        NotebookTags result = redisNotebookTagsTemplate.opsForValue()
                .get(AppConstants.NOTEBOOK_TAGS_PREFIX+id);
        return result;
    }

    public Set<String> getKeysByPattern(String pattern){
        return redisNotebookTagsTemplate.keys(pattern);
    }

    public Boolean deleteNotebookTags(String key){
        return redisNotebookTagsTemplate.delete(key);
    }




}

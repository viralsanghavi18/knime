package com.nevernote.configuration;

import com.nevernote.entity.Note;
import com.nevernote.entity.Notebook;
import com.nevernote.entity.NotebookTags;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Note> redisNoteTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Note> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, Notebook> redisNotebookTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Notebook> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, NotebookTags> redisNotebookTagsTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, NotebookTags> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}

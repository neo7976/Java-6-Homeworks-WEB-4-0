package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

    //todo убрать и реализовать через мапу
//    private final List<Post> list = new CopyOnWriteArrayList<>();
    private final AtomicLong aLong;
    private final Map<Long, Post> maps;

    public PostRepository() {
        this.aLong = new AtomicLong(0);
        this.maps = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        return new ArrayList<>(maps.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(maps.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(aLong.incrementAndGet());
            maps.put(post.getId(), post);
        } else if (maps.containsKey(post.getId())) {
            maps.put(post.getId(), post);
        } else {
            throw new NotFoundException(String.format("POST c id=%s не сушествует", post.getId()));
        }
        return post;
    }


    public void removeById(long id) {
        if (maps.containsKey(id)) {
            maps.remove(id);
        } else
            throw new NotFoundException(String.format("Невозможно найти и удалить POST c id=%s", id));
    }
}

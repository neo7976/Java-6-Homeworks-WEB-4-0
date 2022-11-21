package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
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
        return new ArrayList<>(maps.values()).stream().filter(x -> !x.isRemoved()).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        if (maps.get(id).isRemoved())
            return Optional.empty();
        return Optional.ofNullable(maps.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(aLong.incrementAndGet());
            maps.put(post.getId(), post);
        } else if (maps.containsKey(post.getId()) && !maps.get(post.getId()).isRemoved()) {
            maps.put(post.getId(), post);
        } else {
            throw new NotFoundException(String.format("POST c id=%s не сушествует", post.getId()));
        }
        return post;
    }


    public void removeById(long id) {
        if (maps.containsKey(id)) {
            maps.get(id).setRemoved(true);
        } else
            throw new NotFoundException(String.format("Невозможно найти и удалить POST c id=%s", id));
    }
}

package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    //    private final List<Post> list = new ArrayList<>();
//    private final Set<Post> map = ConcurrentHashMap.newKeySet();
    private final List<Post> list = new CopyOnWriteArrayList<>();
    private final AtomicLong aLong = new AtomicLong(0);

    public List<Post> all() {
        if (!list.isEmpty())
            return list;
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        for (Post post : list) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(aLong.incrementAndGet());
            list.add(post);
        } else if (list.contains(post)) {
            int x = list.indexOf(post);
            list.set(x, post);
        } else {
            throw new NotFoundException(String.format("POST c id=%s не сушествует", post.getId()));
        }
        return post;
    }


    public void removeById(long id) {
        if (!list.isEmpty()) {
            if (getById(id).isPresent())
                list.removeIf((x) -> (x.getId() == id));
            else
                throw new NotFoundException(String.format("Невозможно найти и удалить POST c id=%s", id));
        } else
            throw new NotFoundException("Постов не существует!");
    }
}

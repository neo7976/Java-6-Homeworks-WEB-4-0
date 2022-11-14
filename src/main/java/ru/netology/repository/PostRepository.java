package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final List<Post> list = new ArrayList<>();
    private final AtomicLong aLong = new AtomicLong(0);

    public List<Post> all() {
        if (!list.isEmpty())
            return list;
        return Collections.emptyList();
    }

    public synchronized Optional<Post> getById(long id) {
        for (Post post : list) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public synchronized Post save(Post post) {
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


    public synchronized void removeById(long id) {
        if (!list.isEmpty()) {
            if (getById(id).isPresent())
                list.removeIf((x) -> (x.getId() == id));
            else
                throw new NotFoundException(String.format("Невозможно найти и удалить POST c id=%s", id));
        }
        else
            throw new NotFoundException("Постов не существует!");
    }
}

package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private List<Post> list = new ArrayList<>();
    private final AtomicLong aLong = new AtomicLong(0);

    public List<Post> all() {
        if (!list.isEmpty())
            return list;
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {

        return Optional.empty();
    }

    public synchronized Post save(Post post) {
        //todo если id поста нет, то сохранить как следующий или выдать ошибку
        if (post.getId() == 0 || !list.contains(post)) {
            post.setId(aLong.incrementAndGet());
            list.add(post);
        }
        int x = list.indexOf(post);
        list.set(x, post);
        return post;
    }


    public void removeById(long id) {
    }
}

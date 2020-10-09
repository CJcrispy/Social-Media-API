package Taku.app.core.services.feed;

import Taku.app.core.exception.PostNotFoundException;
import Taku.app.core.models.feed.Post;
import Taku.app.core.payload.request.PostRequest;
import Taku.app.core.repositories.PostRepository;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<PostRequest> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public List<PostRequest> showAllUserPosts(String username) {
        List<Post> posts = postRepository.findAllByUsername(username);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostRequest postRequest) {
        Post post = mapFromDtoToPost(postRequest);
        postRepository.save(post);
    }

    @Transactional
    public PostRequest readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostRequest mapFromPostToDto(Post post) {
        PostRequest postRequest = new PostRequest();
        postRequest.setId(post.getId());
        postRequest.setTitle(post.getTitle());
        postRequest.setContent(post.getContent());
        postRequest.setUsername(post.getUsername());
        return postRequest;
    }

    private Post mapFromDtoToPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCreatedOn(Instant.now());
        post.setUsername(postRequest.getUsername());
        post.setUser(userRepository.findByUsername(postRequest.getUsername()));
        post.setUpdatedOn(Instant.now());
        return post;
    }
}

package Taku.app.core.services.feed;

import Taku.app.core.models.feed.Post;
import Taku.app.core.payload.request.PostRequest;
import Taku.app.core.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public List<PostRequest> showAllPosts() {
        List<Post> posts = postRepository.findAll();
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
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }
}

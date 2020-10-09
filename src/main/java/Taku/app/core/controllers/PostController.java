package Taku.app.core.controllers;

import Taku.app.core.payload.request.PostRequest;
import Taku.app.core.services.feed.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping()
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostRequest>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/all/username={username}")
    public ResponseEntity<List<PostRequest>> showAllUserPosts(@PathVariable @RequestBody String username) {
        return new ResponseEntity<>(postService.showAllUserPosts(username), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostRequest> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }
}

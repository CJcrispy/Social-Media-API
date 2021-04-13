package Taku.app.core.controllers;

import Taku.app.core.services.profile.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController{

    @Autowired
    SocialService socialService;

    @GetMapping("/discoverPage")
    public ResponseEntity<?> discoverPage(){
        return new ResponseEntity<>(socialService.search_random_users(), HttpStatus.OK);
    }

    @GetMapping("/business/profile")
    public ResponseEntity<?> discoverBusiness(){
        return new ResponseEntity<>(socialService.search_business(), HttpStatus.OK);
    }

    @GetMapping("/member/profile")
    public ResponseEntity<?> discoverMember(){
        return new ResponseEntity<>(socialService.search_member(), HttpStatus.OK);
    }

    @GetMapping("/user={id}")
    public ResponseEntity<?> findUser(@PathVariable @RequestBody Long id){
        return new ResponseEntity<>(socialService.find_user(id), HttpStatus.OK);
    }
}

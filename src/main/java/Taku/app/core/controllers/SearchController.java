package Taku.app.core.controllers;

import Taku.app.core.services.profile.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
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

        socialService.test();

        return null;
    }

    @GetMapping("/business/profile")
    public ResponseEntity<?> discoverBusiness(){

        return null;
    }

    @GetMapping("/member/profile")
    public ResponseEntity<?> discoverMember(){

        return null;
    }
}

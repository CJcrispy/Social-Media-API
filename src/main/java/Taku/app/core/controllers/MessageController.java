package Taku.app.core.controllers;

import Taku.app.core.services.feed.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/dialog/{id}")
    public ResponseEntity<?> getDialog(){


    }

    @GetMapping("/recentMessage")
    public ResponseEntity<?> getRecentMessage(){

    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> send(){

    }
}

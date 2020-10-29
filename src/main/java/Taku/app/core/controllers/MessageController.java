package Taku.app.core.controllers;

import Taku.app.core.payload.request.RequestByUsername;
import Taku.app.core.payload.request.MessageRequest;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.services.feed.MessageService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/dialog/{id}")
    public ResponseEntity<?> getDialog(@PathVariable @RequestBody Long id){

        return ResponseEntity.ok(new MessageResponse("Message sent successfully!"));
    }

    @JsonIgnore
    @GetMapping("/recentMessage/{id}")
    public ResponseEntity<?> getRecentMessage(@PathVariable @RequestBody Long id){
        messageService.getLastMessages(id);

        return new ResponseEntity<>(messageService.getLastMessages(id), HttpStatus.OK);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> send(@Valid @RequestBody MessageRequest messageRequest){
        messageService.send(messageRequest);

        return ResponseEntity.ok(new MessageResponse("Message sent successfully!"));
    }
}

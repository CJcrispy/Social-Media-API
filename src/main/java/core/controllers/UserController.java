package core.controllers;

import core.DTO.LoginDTO;
import core.DTO.TokenDTO;
import core.DTO.UserDTO;
import core.entities.Token;
import core.entities.User;
import core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.xml.ws.http.HTTPException;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return Optional.ofNullable(this.userService.getById(id))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login/")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO loginUser(@RequestBody LoginDTO credentials) {
        System.out.println("logging in");
        Token ret = this.userService.login(credentials.getUsername(),credentials.getPassword());
        if (ret == null){
            throw new HTTPException(401);
        }
        System.out.println("id: " + ret.getId());
        System.out.println("user id: " + ret.getUser().getId());
        System.out.println("username: " + ret.getUser().getUserName());

        TokenDTO dto = new TokenDTO(ret.getId(), ret.getUser().getId(), ret.getUser().getUserName(), ret.getExpiration());
        return dto;
    }

    @PostMapping("/register/")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDTO user) {
        this.userService.register(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        return this.userService.update(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable int id) {
        return this.userService.deleteById(id);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}

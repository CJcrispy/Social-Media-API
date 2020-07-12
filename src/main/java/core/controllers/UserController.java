package core.controllers;

import core.DTO.LoginDTO;
import core.DTO.TokenDTO;
import core.DTO.MemberDTO;
import core.entities.MemberToken;
import core.entities.Member;
import core.services.AuthenticationService;
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
    private AuthenticationService authService;

    @GetMapping("/{id}")
    public Member getById(@PathVariable int id) {
        return Optional.ofNullable(this.authService.getById(id))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login/")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO loginUser(@RequestBody LoginDTO credentials) {
        System.out.println("logging in");
        MemberToken ret = this.authService.login(credentials.getEmail(),credentials.getPassword());
        if (ret == null){
            throw new HTTPException(401);
        }
        System.out.println("id: " + ret.getId());
        System.out.println("user id: " + ret.getMember().getId());
        System.out.println("email: " + ret.getMember().getEmail());

        TokenDTO dto = new TokenDTO(ret.getId(), ret.getMember().getId(), ret.getMember().getEmail(), ret.getExpiration());
        return dto;
    }

    @PostMapping("/register/")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody MemberDTO user) {
        this.authService.register(user);
    }

    @PutMapping("")
    public Member updateUser(@RequestBody Member member) {
        return this.authService.update(member);
    }

    @DeleteMapping("/{id}")
    public Member deleteUser(@PathVariable int id) {
        return this.authService.deleteById(id);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}

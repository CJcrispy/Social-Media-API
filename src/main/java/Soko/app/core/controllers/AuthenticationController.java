package Soko.app.core.controllers;

import Soko.app.core.DTO.BusinessDTO;
import Soko.app.core.DTO.LoginDTO;
import Soko.app.core.DTO.MemberDTO;
import Soko.app.core.DTO.TokenDTO;
import Soko.app.core.entities.BusinessToken;
import Soko.app.core.services.AuthenticationService;
import Soko.app.core.entities.Member;
import Soko.app.core.entities.MemberToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.xml.ws.http.HTTPException;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping("/{id}")
    public Member getById(@PathVariable int id) {
        return Optional.ofNullable(this.authService.getById(id))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenDTO loginUser(@RequestBody LoginDTO credentials) {
        System.out.println("Checking crendentials...");
        String role = this.authService.checkRole(credentials.getEmail());

        if (role == "member"){

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

        } else if(role == "business"){

            System.out.println("logging in");
            BusinessToken ret = this.authService.businessLogin(credentials.getEmail(),credentials.getPassword());
            if (ret == null){
                throw new HTTPException(401);
            }
            System.out.println("id: " + ret.getId());
            System.out.println("business id: " + ret.getBusiness().getBusiness_id());
            System.out.println("email: " + ret.getBusiness().getBusiness_email());

            TokenDTO dto = new TokenDTO(ret.getId(), ret.getBusiness().getBusiness_id(), ret.getBusiness().getBusiness_email(), ret.getExpiration());
            return dto;
        } else {

        }

        return null;
    }

    @PostMapping("/register-member/")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerMember(@RequestBody MemberDTO user) {
        this.authService.registerMember(user);
    }

    @PostMapping("/register-business/")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBusiness(@RequestBody BusinessDTO user) {
        this.authService.registerBusiness(user);
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

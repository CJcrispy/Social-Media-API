package Taku.app.core.controllers;

import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.users.ERole;
import Taku.app.core.models.users.Roles;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.*;
import Taku.app.core.payload.response.JwtResponse;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.repositories.RoleRepository;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.repositories.VerificationTokenRepository;
import Taku.app.core.security.JwtUtils;
import Taku.app.core.services.profile.ProfileService;
import Taku.app.core.services.userDetails.EmailSenderService;
import Taku.app.core.services.userDetails.UserDetailsImpl;
import Taku.app.core.services.userDetails.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    ProfileService profileService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getFirst_name(),
                userDetails.getLast_name(),
                userDetails.getBusiness_name(),
                userDetails.getEmail(),
                userDetails.isVerified(),
                userDetails.getBio(),
                userDetails.getOccupation(),
                userDetails.getLink(),
                userDetails.getFollowers(),
                userDetails.getFollowing(),
                roles));
    }

    @PostMapping("/register-member")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest, ModelAndView modelAndView) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account and logs info into database
        User user = new User(signUpRequest.getUsername(), signUpRequest.getFirst_name(), signUpRequest.getLast_name(),
                signUpRequest.getBusiness_name(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByName(ERole.Role_member)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "business":
                        Roles adminRole = roleRepository.findByName(ERole.Role_business)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "admin":
                        Roles modRole = roleRepository.findByName(ERole.Role_Admin)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Roles userRole = roleRepository.findByName(ERole.Role_member)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        //Creates User profile
        profileService.createProfile(user);

        //Sends out validation email
        VerificationToken verificationToken = new VerificationToken(user);

        verificationTokenRepository.save(verificationToken);

        emailSenderService.sendEmail(signUpRequest.getEmail());

        modelAndView.addObject("emailId", signUpRequest.getEmail());

        modelAndView.setViewName("successfulRegisteration");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/register-business")
    public ResponseEntity<?> registerBusiness(@Valid @RequestBody RegisterRequest signUpRequest, ModelAndView modelAndView) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account and logs info into database
        User user = new User(signUpRequest.getUsername(), signUpRequest.getFirst_name(), signUpRequest.getLast_name(),
                signUpRequest.getBusiness_name(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByName(ERole.Role_business)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "member":
                        Roles adminRole = roleRepository.findByName(ERole.Role_member)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "admin":
                        Roles modRole = roleRepository.findByName(ERole.Role_Admin)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Roles userRole = roleRepository.findByName(ERole.Role_business)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        //Creates User profile
        profileService.createProfile(user);

        //Sends out validation email
        VerificationToken verificationToken = new VerificationToken(user);

        verificationTokenRepository.save(verificationToken);

        emailSenderService.sendEmail(signUpRequest.getEmail());

        modelAndView.addObject("emailId", signUpRequest.getEmail());

        modelAndView.setViewName("successfulRegisteration");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/verify-email")
    public String verifyEmail(@Valid @RequestBody CodeVerificationRequest codeVerificationRequest) {

        return verificationTokenService.verifyEmail(codeVerificationRequest.getToken()).getBody();
    }

    @PostMapping("/retry-validation")
    public ResponseEntity<?> retryEmailValidation(@Valid @RequestBody RequestByEmail requestByEmail,
                                                   ModelAndView modelAndView){
        if (userRepository.existsByEmail(requestByEmail.getEmail())){

            emailSenderService.retryEmail(requestByEmail.getEmail());

            modelAndView.addObject("emailId", requestByEmail.getEmail());

            modelAndView.setViewName("successfulRegisteration");
        } else{
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email does not exist!"));
        }

        return ResponseEntity.ok(new MessageResponse("retry email validation link completed successfully!"));
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest,
                                            @PathVariable @RequestBody Long id){
        profileService.changePassword(passwordResetRequest.getNew_password(), id);

        return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
    }

}

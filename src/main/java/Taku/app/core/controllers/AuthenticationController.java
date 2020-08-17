package Taku.app.core.controllers;

import Taku.app.core.models.email_verification.MailProperties;
import Taku.app.core.models.email_verification.VerificationToken;
import Taku.app.core.models.users.*;
import Taku.app.core.models.email_verification.VerificationForm;
import Taku.app.core.payload.request.*;
import Taku.app.core.payload.response.*;
import Taku.app.core.repositories.*;
import Taku.app.core.security.JwtUtils;
import Taku.app.core.services.userDetails.EmailSenderService;
import Taku.app.core.services.userDetails.UserDetailsImpl;
import Taku.app.core.models.users.User;
import Taku.app.core.services.userDetails.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
    JwtUtils jwtUtils;

    @Autowired
    MailProperties mailProperties;

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
                roles));
    }

    @PostMapping("/register-member")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFirst_name(), signUpRequest.getLast_name(),
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

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/register-business")
    public ResponseEntity<?> registerBusiness(@Valid @RequestBody RegisterRequest signUpRequest, ModelAndView modelAndView) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFirst_name(), signUpRequest.getLast_name(),
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

//        verificationTokenService.createVerification(signUpRequest.getEmail());

        VerificationToken verificationToken = new VerificationToken(user);

        verificationTokenRepository.save(verificationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        System.out.println("mail to whom: " + signUpRequest.getEmail());
        mailMessage.setTo(signUpRequest.getEmail());

        mailMessage.setSubject("Complete Registration!");

        mailMessage.setFrom(mailProperties.getUsername());
        System.out.println("mail to whom: " + mailProperties.getUsername());

        mailMessage.setText("To confirm your account, please click here : "
                + mailProperties.getVerificationapi() + verificationToken.getConfirmationToken());


        System.out.println("To confirm your account, please click here : "
                + "http://localhost:8081/verify-email?token="+ verificationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage, signUpRequest.getEmail());

        modelAndView.addObject("emailId", signUpRequest.getEmail());

        modelAndView.setViewName("successfulRegisteration");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    @GetMapping("/email-verification")
////    public String formGet(Model model) {
////        model.addAttribute("verificationForm", new VerificationForm());
////        return "verification-form";
////    }
////
////    @PostMapping("/email-verification")
////    public String formPost(@Valid @RequestBody VerificationForm verificationForm, BindingResult bindingResult, Model model) {
////
////        System.out.println("email that needs to be verified: " + verificationForm.getEmail());
////        if (!bindingResult.hasErrors()) {
////            model.addAttribute("noErrors", true);
////        }
////        model.addAttribute("verificationForm", verificationForm);
////
////        verificationTokenService.createVerification(verificationForm.getEmail());
////        return "verification-form";
////    }

    @GetMapping("/verify-email")
    @ResponseBody
    public String verifyEmail(String code) {
        return verificationTokenService.verifyEmail(code).getBody();
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, User user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, User user)
    {

//        User existingUser = userRepository.findByEmail(user.getEmail());
//        if(existingUser != null)
//        {
//            modelAndView.addObject("message","This email already exists!");
//            modelAndView.setViewName("error");
//        }
//        else
//        {
//            userRepository.save(user);
//
//            VerificationToken verificationToken = new VerificationToken();
//
//            verificationTokenRepository.save(verificationToken);
//
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(user.getEmail());
//            mailMessage.setSubject("Complete Registration!");
//            mailMessage.setFrom(MailProperties.
//                    );
//            mailMessage.setText("To confirm your account, please click here : "
//                    +"http://localhost:8082/confirm-account?token="+ verificationToken.getConfirmationToken());
//
//            emailSenderService.sendEmail(mailMessage);
//
//            modelAndView.addObject("emailId", user.getEmail());
//
//            modelAndView.setViewName("successfulRegisteration");
//        }

        return modelAndView;
    }






}

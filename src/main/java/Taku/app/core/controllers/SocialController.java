package Taku.app.core.controllers;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.FollowerRequest;
import Taku.app.core.payload.request.ProfileRequest;
import Taku.app.core.payload.request.RequestByEmail;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.payload.response.ProfileResponse;
import Taku.app.core.repositories.ProfileRepository;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.services.images.AmazonClient;
import Taku.app.core.services.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/social")
public class SocialController {


    private AmazonClient amazonClient;

    @Autowired
    SocialController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    //Profile
    @PostMapping("/createProfile")
    public ResponseEntity<?> createProfile(@Valid @RequestBody RequestByEmail requestByEmail){

        if (userRepository.existsByEmail(requestByEmail.getEmail())){
            User user = userRepository.findByEmailIgnoreCase(requestByEmail.getEmail());
            if (user.getProfile() == null) {
                profileService.createProfile(user);
                Profile profile = new Profile(user);
                profileRepository.save(profile);
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Profile already exist for this user!"));
            }

        }

        
        return null;
    }

    @GetMapping("/retrieveProfile")
//    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> getProfile(@Valid @RequestBody RequestByEmail requestByEmail) {

        if (userRepository.existsByEmail(requestByEmail.getEmail())) {
            User user = userRepository.findByEmailIgnoreCase(requestByEmail.getEmail());
            Profile profile = profileRepository.findByUser(user);
            return ResponseEntity.ok(new ProfileResponse(
                    user.getId(), user.getFirst_name(), user.getLast_name(),
                    user.getBusiness_name(), user.getEmail(), user.isVerified(),
                    profile.getBio(), profile.getOccupation(),
                    profile.getFollowers() ,profile.getFollowing(), profile.getLink()));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }


    }

    @PutMapping("/updateProfile")
//    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileRequest profileRequest){

        if (userRepository.existsByEmail(profileRequest.getEmail())) {
            System.out.println("test: " + profileRequest.getEmail());
            System.out.println("test 2: " + profileRequest.getBio());
            System.out.println("test 3: " + profileRequest.getOccupation());
            System.out.println("test 4: " + profileRequest.getProfileLink());
            profileService.validateProfile(profileRequest);
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }



        return ResponseEntity.ok(new MessageResponse("retry email validation link completed successfully!"));
    }

    //admin only api
    @DeleteMapping("/admin/deleteProfile")
    @PreAuthorize("hasRole('Admin')")
    public String deleteProfile(){
        return null;
    }



    //Follows
    @GetMapping("/getFollows")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> getFollows(@Valid @RequestBody FollowerRequest followerRequest){

        if (userRepository.existsByEmail(followerRequest.getUserEmail()) == true
        && userRepository.existsByEmail(followerRequest.getFollowerEmail()) == true){

            User user = userRepository.findByEmailIgnoreCase(followerRequest.getUserEmail());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }

        return null;
    }
    @PostMapping("/addFollows")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> addFollows(@Valid @RequestBody FollowerRequest followerRequest){

        if (userRepository.existsByEmail(followerRequest.getUserEmail()) == true
                && userRepository.existsByEmail(followerRequest.getFollowerEmail()) == true){

            User user = userRepository.findByEmailIgnoreCase(followerRequest.getUserEmail());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }
        return null;
    }
    @DeleteMapping("removeFollows")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> removeFollows(@Valid @RequestBody FollowerRequest followerRequest){

        if (userRepository.existsByEmail(followerRequest.getUserEmail()) == true
                && userRepository.existsByEmail(followerRequest.getFollowerEmail()) == true){

            User user = userRepository.findByEmailIgnoreCase(followerRequest.getUserEmail());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }

        return null;
    }




    //Pictures
    @PostMapping("/uploadProfilePic")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadProfilePic(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @PostMapping("/uploadPhoto")
    //@PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadPhoto(@RequestPart(value = "file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deletePhoto")
    //@PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}

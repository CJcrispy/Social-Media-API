package Taku.app.core.controllers;

import Taku.app.core.models.profile.Network;
import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.FollowerRequest;
import Taku.app.core.payload.request.ProfileRequest;
import Taku.app.core.payload.request.RequestByEmail;
import Taku.app.core.payload.response.FollowerResponse;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.payload.response.ProfileResponse;
import Taku.app.core.repositories.NetworkRepository;
import Taku.app.core.repositories.ProfileRepository;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.services.images.AmazonClient;
import Taku.app.core.services.profile.NetworkService;
import Taku.app.core.services.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

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
    NetworkService networkService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NetworkRepository networkRepository;

    //Profile
    @GetMapping("/retrieveProfile/{id}")
    //@PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> getProfile(@PathVariable @RequestBody Long id) {

        try {
            Optional<User> person = userRepository.findById(id);
            User user = person.get();
            if (userRepository.existsByEmail(user.getEmail()) == true) {
                Profile profile = profileRepository.findByUser(user.getId());

                return ResponseEntity.ok(new ProfileResponse(
                        user.getId(),user.getUsername() ,user.getFirst_name(), user.getLast_name(),
                        user.getBusiness_name(), user.getEmail(),
                        profile.getBio(), profile.getOccupation(),
                        profile.getFollowers(), profile.getFollowing(), profile.getLink()));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Something with wrong"));
        }

    }

    @PutMapping("/updateProfile")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
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


    //Follows
    @GetMapping("/getFollows")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> getFollows(@Valid @RequestBody RequestByEmail requestByEmail){

        if (userRepository.existsByEmail(requestByEmail.getEmail()) == true){

            User user = userRepository.findByEmailIgnoreCase(requestByEmail.getEmail());
            return ResponseEntity.ok(new FollowerResponse(
                    networkService.countFollowers(user) , networkService.countFollowing(user)));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }

    }

    @PostMapping("/addFollows")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> addFollows(@Valid @RequestBody FollowerRequest followerRequest){

        if (userRepository.existsByEmail(followerRequest.getUserEmail()) == true
                && userRepository.existsByEmail(followerRequest.getFollowerEmail()) == true){

            //user 1
            User user = userRepository.findByEmailIgnoreCase(followerRequest.getUserEmail());

            //user 2 (Person user 1 is attempting to follow)
            User user2 = userRepository.findByEmailIgnoreCase(followerRequest.getFollowerEmail());

            if (networkService.isFollowing(user, user2) == false) {
                //adding user2 to user1 following list
                Network network = new Network(user, user2);
                network.setRelationships(Network.STATUS_FOLLOWING);
                networkRepository.save(network);

                Network network2 = new Network(user2, user);
                network2.setRelationships(Network.STATUS_FOLLOWED);
                networkRepository.save(network2);

            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("User is already following this target"));
            }

            return ResponseEntity.ok(new MessageResponse("User successfully followed target user!"));

        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }
    }

    @DeleteMapping("/removeFollows")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> removeFollows(@Valid @RequestBody FollowerRequest followerRequest){

        if (userRepository.existsByEmail(followerRequest.getUserEmail()) == true
                && userRepository.existsByEmail(followerRequest.getFollowerEmail()) == true){

            //user 1
            User user = userRepository.findByEmailIgnoreCase(followerRequest.getUserEmail());

            //user 2 (Person user 1 is attempting to follow)
            User user2 = userRepository.findByEmailIgnoreCase(followerRequest.getFollowerEmail());

            //adding user2 to user1 following list
            networkService.removeFollowing(user, user2);

            return ResponseEntity.ok(new MessageResponse("User successfully unfollowed target user!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }

    }


    //Pictures
    @PostMapping("/uploadProfilePic/{id}")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadProfilePic(@RequestPart(value = "file") MultipartFile file,
                                   @PathVariable @RequestBody Long id) {
        return this.amazonClient.uploadProfilePic(file, id);
    }

    @PostMapping("/uploadCoverImage/{id}")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadCoverImage(@RequestPart(value = "file") MultipartFile file,
                                   @PathVariable @RequestBody Long id) {
        return this.amazonClient.uploadCoverImage(file, id);
    }

    @PostMapping("/uploadPhoto/{id}")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadPhoto(@RequestPart(value = "file") MultipartFile file,
                              @PathVariable @RequestBody Long id){
        return this.amazonClient.uploadFile(file, id);
    }

    @DeleteMapping("/deletePhoto")
    @PreAuthorize("hasRole('Admin')")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}

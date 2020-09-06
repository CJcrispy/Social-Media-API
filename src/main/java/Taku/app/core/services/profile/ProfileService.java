package Taku.app.core.services.profile;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.ProfileRequest;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.repositories.ProfileRepository;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> createProfile(User user){

        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return null;
    }


    public ResponseEntity<?> validateProfile(ProfileRequest profile){

       if (userRepository.exisitsById(profile.getUserId()) == true) {
           Optional<User> user = userRepository.findById(profile.getUserId());
           System.out.println("test: " + user.get().getEmail());
//           updateProfile(user, profile);
       } else {
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: Email is already in use!"));
       }

        return ResponseEntity.ok(new MessageResponse("User validated successfully!"));
    }

    public ResponseEntity<String> updateProfile(User user, ProfileRequest profile){

        List<Profile> profileList = profileRepository.findByUser(user);
        Profile profilez = profileList.get(0);
        profilez.setBio(profile.getBio());
        profilez.setOccupation(profile.getOccupation());
        profilez.setLink(profile.getProfileLink());
        profileRepository.save(profilez);

        return ResponseEntity.ok("You have successfully updated your profile.");
    }

}

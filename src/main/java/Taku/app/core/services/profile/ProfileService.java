package Taku.app.core.services.profile;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.repositories.ProfileRepository;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> createProfile(User user){

        List<Profile> profileList = profileRepository.findByUser(user);


        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return null;
    }


    public ResponseEntity<?> validateProfile(Long id){

       if (userRepository.exisitsById(id) == true) {
           User user = userRepository.findByUserId(id);

           updateProfile(user);
       } else {
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: Email is already in use!"));
       }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<String> updateProfile(User user){ return null; }

}

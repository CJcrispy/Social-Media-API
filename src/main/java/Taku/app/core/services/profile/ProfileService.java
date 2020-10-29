package Taku.app.core.services.profile;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.ProfileRequest;
import Taku.app.core.payload.response.MessageResponse;
import Taku.app.core.repositories.ProfileRepository;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    public void changePassword(String password, Long id){
        try{
            Optional<User> person = userRepository.findById(id);
            User user = person.get();
            encoder.encode(password);

            userRepository.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean validateProfile(User user){

        boolean profile;
        if (profileRepository.findByUser(user.getId()) == null){
            profile = false;
        } else {
            profile = true;
        }

        return profile;
    }


    public ResponseEntity<String> createProfile(User user)  {

        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return null;
    }


    public ResponseEntity<?> validateProfile(ProfileRequest profile){

       if (userRepository.existsByEmail(profile.getEmail()) == true) {
           User user = userRepository.findByEmailIgnoreCase(profile.getEmail());
           updateProfile(user, profile);
           return ResponseEntity.ok("You have successfully updated your profile.");
       } else {
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: User does not exist"));
       }

    }

    public void updateProfile(User user, ProfileRequest profile){

        Profile profileList = profileRepository.findByUser(user.getId());
        profileList.setBio(profile.getBio());
        profileList.setOccupation(profile.getOccupation());
        profileList.setLink(profile.getProfileLink());
        profileRepository.save(profileList);

    }

}

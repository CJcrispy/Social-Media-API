package Taku.app.core.services.profile;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    //we want random alphanumeric string of 20 characters
    private static final int OUTPUT_STRING_LENGTH = 20;

    public String generateProfileLink(){
        //string containing allowed characters, modify according to your needs
        String strAllowedCharacters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //initialize Random
        Random random = new Random();
        System.out.println("Random alphanumeric string of 20 characters");
        for(int i=0; i<10; i++){
            System.out.println( getNextRandomString(strAllowedCharacters, random) );
        }
        return null;
    }


    private static String getNextRandomString(String strAllowedCharacters, Random random) {
        StringBuilder sbRandomString = new StringBuilder(OUTPUT_STRING_LENGTH);
        for(int i = 0 ; i < OUTPUT_STRING_LENGTH; i++){
            //get random integer between 0 and string length
            int randomInt = random.nextInt(strAllowedCharacters.length());

            //get char from randomInt index from string and append in StringBuilder
            sbRandomString.append( strAllowedCharacters.charAt(randomInt) );
        }
        return sbRandomString.toString();
    }


    public String createProfile(User user){

        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return null;
    }
}

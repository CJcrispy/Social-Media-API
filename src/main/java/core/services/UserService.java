package core.services;

import core.DTO.UserDTO;
import core.entities.Token;
import core.entities.User;
import core.repositories.TokenRepository;
import core.repositories.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    TokenRepository tokenRepository;

    private String getSaltstring() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while(salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private String hash(String password, String salt) {
        String sha256hex = Hashing.sha256()
                .hashString(password+salt, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }

    public User getById(int id) {
        return this.userRepository.getById(id);
    }

    public User create(User user) {
        return this.userRepository.create(user);
    }

    public User update(User user) {
        return this.userRepository.update(user);
    }

    public User deleteById(int id) {
        return this.userRepository.deleteById(id);
    }

    public Token login(String username, String password) {
        User toCheck = this.userRepository.getByUsername(username);
        if(toCheck.getHashedPass().equals(hash(password, toCheck.getSalt()))) {
            return this.tokenRepository.newToken(toCheck);
        }
        return null;
    }

    public void register(UserDTO user) {
        String salt = getSaltstring();
        User toAdd = new User(user.getFname(), user.getLname(), user.getUsername(), hash(user.getPassword(),salt), salt, user.getEmail());
        this.userRepository.create(toAdd);
    }
}

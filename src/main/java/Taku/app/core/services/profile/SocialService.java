package Taku.app.core.services.profile;

import Taku.app.core.models.users.User;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    UserRepository userRepository;

    public void test(){
        long n = userRepository.grabTableRowCount();
        System.out.println("n = " + n);
        List<User> testing = userRepository.grabRandomTableRows(n);

        // Print the name from the list....
        for(User user : testing) {
            System.out.println("first name = " + user.getFirst_name() );
            System.out.println("last name = " + user.getLast_name() );
            System.out.println("email = " + user.getEmail());
            System.out.println("business name = " + user.getBusiness_name() );
        }
    }

    public void test_business(){
        long n = userRepository.grabTableRowCount();
        System.out.println("n = " + n);
        List<User> testing = userRepository.grabRandomBusinessTableRows(n);

        // Print the name from the list....
        for(User user : testing) {
            System.out.println("first name = " + user.getFirst_name() );
            System.out.println("last name = " + user.getLast_name() );
            System.out.println("email = " + user.getEmail());
            System.out.println("business name = " + user.getBusiness_name() );
        }
    }

    public void test_member() {
        long n = userRepository.grabTableRowCount();
        System.out.println("n = " + n);
        List<User> testing = userRepository.grabRandomMemberTableRows(n);

        // Print the name from the list....
        for(User user : testing) {
            System.out.println("first name = " + user.getFirst_name() );
            System.out.println("last name = " + user.getLast_name() );
            System.out.println("email = " + user.getEmail());
            System.out.println("business name = " + user.getBusiness_name() );
        }
    }

}

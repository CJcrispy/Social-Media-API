package Taku.app.core.services.profile;

import Taku.app.core.models.users.User;
import Taku.app.core.repositories.NetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkService {

    @Autowired
    private NetworkRepository networkRepository;

    public long countFollowers(User user) {
        return networkRepository.getFollowers(user);
    }

    public long countFollowing(User user) {
        return networkRepository.getFollowing(user);
    }

    public boolean isFollowing(User user1, User user2) {
        if(networkRepository.isFollowing(user1, user2) > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean removeFollowing(User user1, User user2){
        networkRepository.removeFollower(user1, user2);
        networkRepository.removeFollowing(user1, user2);
        if(networkRepository.isFollowing(user1, user2) == 0){
            return false;
        } else {
            return true;
        }
    }

}

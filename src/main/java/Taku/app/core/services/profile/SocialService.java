package Taku.app.core.services.profile;

import Taku.app.core.models.feed.Messages;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.response.ConversationResponse;
import Taku.app.core.payload.response.DiscoveryPageResponse;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SocialService {

    @Autowired
    UserRepository userRepository;

    public List<DiscoveryPageResponse> search_random_users(){
        List<User> users = userRepository.grabRandomTableRows();
        return users.stream().map(this::mapFromRequestToDto).collect(toList());
    }

    public List<DiscoveryPageResponse> search_business(){
        List<User> users = userRepository.grabBusinessUsers();
        return users.stream().map(this::mapFromRequestToDto).collect(toList());
    }

    public List<DiscoveryPageResponse> search_member() {
        List<User> users = userRepository.grabMemberUsers();
        return users.stream().map(this::mapFromRequestToDto).collect(toList());
    }

    public DiscoveryPageResponse find_user(Long id) {
        Optional<User> person = userRepository.findById(id);
        User user = person.get();
        DiscoveryPageResponse discoveryPageResponse = new DiscoveryPageResponse();
        discoveryPageResponse.setUsername(user.getUsername());
        discoveryPageResponse.setImage_reference(user.getProfile().getAvatar_reference());
        discoveryPageResponse.setFollowers(user.getProfile().getFollowers());
        discoveryPageResponse.setFollowing(user.getProfile().getFollowing());
        return discoveryPageResponse;
    }

    private DiscoveryPageResponse mapFromRequestToDto(User user) {
        DiscoveryPageResponse discoveryPageResponse = new DiscoveryPageResponse();
        discoveryPageResponse.setUsername(user.getUsername());
        discoveryPageResponse.setImage_reference(user.getProfile().getAvatar_reference());
        discoveryPageResponse.setFollowers(user.getProfile().getFollowers());
        discoveryPageResponse.setFollowing(user.getProfile().getFollowing());
        return discoveryPageResponse;
    }

}

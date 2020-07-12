package core.services;

import core.DTO.*;
import core.entities.*;
import core.repositories.*;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class AuthenticationService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BusinessRepository businessRepository;
    
    @Autowired
    TokenRepository tokenRepository;

    public void register(MemberDTO user) {
        String salt = getSaltstring();
        Member toAdd = new Member(user.getFname(), user.getLname(), user.getEmail(), hash(user.getPassword(),salt), salt);
        this.memberRepository.create(toAdd);
    }

    public void registerBusiness(BusinessDTO businessUser) {

    }

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


    public String checkRole(String email){

        return email;
    }

    public Member getById(int id) {
        return this.memberRepository.getById(id);
    }

    public Member create(Member member) {
        return this.memberRepository.create(member);
    }

    public Member update(Member member) {
        return this.memberRepository.update(member);
    }

    public Member deleteById(int id) {
        return this.memberRepository.deleteById(id);
    }

    public MemberToken login(String email, String password) {
        Member toCheck = this.memberRepository.getByEmail(email);
        if(toCheck.getHashedPass().equals(hash(password, toCheck.getSalt()))) {
            return this.tokenRepository.newMemberUserToken(toCheck);
        }
        return null;
    }

    public BusinessToken businessLogin(String email, String password) {
        Business toCheck = this.businessRepository.getByEmail(email);
        if(toCheck.getHashedPass().equals(hash(password, toCheck.getSalt()))) {
            return this.tokenRepository.newBusinessUserToken(toCheck);
        }
        return null;
    }


}

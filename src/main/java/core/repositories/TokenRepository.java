package core.repositories;

import core.entities.Token;
import core.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TokenRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public Token newToken(User user) {
        Token token = new Token(user, user);
        Session session = sessionFactory.getCurrentSession();
        session.save(token);
        return token;
    }
}

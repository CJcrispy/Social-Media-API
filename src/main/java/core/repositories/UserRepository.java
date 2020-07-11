package core.repositories;

import core.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.xml.ws.http.HTTPException;

@Repository
public class UserRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public User getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User create(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if(user == null) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        session.delete(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User getByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        User user;
        try {
            user = (session.createQuery("Select u from User u where u.userName = :username",User.class).setParameter("username", username).list()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new HTTPException(401);
        }
        return user;
    }

}

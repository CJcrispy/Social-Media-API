package core.repositories;

import core.entities.Business;
import core.entities.Member;
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
public class BusinessRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public Member getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Member.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Member create(Member member) {
        Session session = sessionFactory.getCurrentSession();
        session.save(member);
        return member;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Member update(Member member) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(member);
        return member;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Member deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Member member = session.get(Member.class, id);
        if(member == null) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        session.delete(member);
        return member;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Business getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Business business;
        try {
            business = (session.createQuery("Select e from Business e where e.business_email = :email", Business.class).setParameter("email", email).list()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new HTTPException(401);
        }
        return business;
    }
}

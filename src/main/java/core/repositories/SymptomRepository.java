package core.repositories;

import blue.profit.entities.Symptom;
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
public class SymptomRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public Symptom getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Symptom.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Symptom create(Symptom symptom) {
        Session session = sessionFactory.getCurrentSession();
        session.save(symptom);
        return symptom;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Symptom update(Symptom symptom) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(symptom);
        return symptom;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Symptom deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Symptom user = session.get(Symptom.class, id);
        if(user == null) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        session.delete(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Symptom getBySymptom(String symptom_name) {
        Session session = sessionFactory.getCurrentSession();
        Symptom symptom;
        try {
            symptom = (session.createQuery("Select s from Symptom s where s.symptom_name = :symptom",Symptom.class).setParameter("symptom", symptom_name).list()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new HTTPException(401);
        }
        return symptom;
    }

}

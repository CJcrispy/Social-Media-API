package Soko.app.core.repositories;

import Soko.app.core.entities.Business;
import Soko.app.core.entities.BusinessToken;
import Soko.app.core.entities.MemberToken;
import Soko.app.core.entities.Member;
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
    public MemberToken newMemberUserToken(Member member) {
        MemberToken token = new MemberToken(member, member);
        Session session = sessionFactory.getCurrentSession();
        session.save(token);
        return token;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BusinessToken newBusinessUserToken(Business business) {
        BusinessToken token = new BusinessToken(business, business);
        Session session = sessionFactory.getCurrentSession();
        session.save(token);
        return token;
    }
}

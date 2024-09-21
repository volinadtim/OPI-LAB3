package my.web.database;

import my.web.Crack;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.util.List;

public class HibernateOrm implements Orm {
    private final EntityManager manager;
    private List<Crack> shots;

    public HibernateOrm() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        manager = (EntityManager) sessionFactory.openSession();
    }

    @Override
    public void createCrack(Crack shot) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(shot);
        transaction.commit();
    }

    @Override
    public List<Crack> getCracks(String shooter) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Crack> query = manager.createQuery("SELECT p FROM Crack p WHERE p.shooter = :shooter", Crack.class);
            query = query.setParameter("shooter", shooter);
            transaction.commit();
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        return null;
    }

    @Override
    public void clearCracks(String shooter) {
        TypedQuery<Crack> query = manager.createQuery("DELETE FROM Crack WHERE shots.shooter = :shooter", Crack.class);
        query = query.setParameter("shooter", shooter);
        query.getResultList();
    }
}

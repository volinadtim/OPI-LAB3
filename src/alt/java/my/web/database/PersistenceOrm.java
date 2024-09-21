package my.web.database;

import my.web.Crack;

import javax.persistence.*;
import java.util.List;

public class PersistenceOrm implements Orm {
    private final EntityManager manager;
    private List<Crack> shots;

    public PersistenceOrm() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("db_con");
        manager = managerFactory.createEntityManager();
    }

    @Override
    public void createCrack(Crack shot) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(shot);
        transaction.commit();
        transaction.begin();
    }

    @Override
    public List<Crack> getCracks(String shooter) {
        TypedQuery<Crack> query = manager.createQuery("SELECT shots FROM shots WHERE shots.shooter = :shooter", Crack.class);
        query = query.setParameter("shooter", shooter);
        return query.getResultList();
    }

    @Override
    public void clearCracks(String shooter) {
        TypedQuery<Crack> query = manager.createQuery("DELETE FROM shots WHERE shots.shooter = :shooter", Crack.class);
        query = query.setParameter("shooter", shooter);
        query.getResultList();
    }
}

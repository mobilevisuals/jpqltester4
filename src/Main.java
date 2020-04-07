import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        try {
            for (int i = 0; i < 100; i++) {
                Point point = new Point();
                point.setX(i);
                point.setY(i);
                entityManager.persist(point);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            Main main = new Main();
            // main.getPoints(entityManager);
            Logger logger = Logger.getLogger(Main.class.getName());
            // Point point=main.getPointbyId(2,entityManager);
            // logger.log(Level.INFO,point.toString());
            main.queries(logger, entityManager);
            main.findAll(entityManager, logger);
            main.biggerThanX(56,entityManager,logger);
            entityManager.close();

        }

    }

    private void findAll(EntityManager entityManager, Logger logger) {
        TypedQuery<Point> query = entityManager.createNamedQuery("findall", Point.class);
        List<Point> pointList = query.getResultList();
        for (Point point : pointList
        ) {
            logger.log(Level.INFO, point.toString());
        }


    }

    private void biggerThanX(int x, EntityManager entityManager, Logger logger)
    {
        TypedQuery<Point> typedQuery=entityManager.createNamedQuery("biggerThanX",Point.class);
        typedQuery.setParameter("x",x);
        List<Point> pointList = typedQuery.getResultList();

        for (Point point : pointList
        ) {
            logger.log(Level.INFO, point.toString());
        }
    }

    private void queries(Logger logger, EntityManager entityManager) {
        Query query = entityManager.createQuery("select count(p) from Point p");
        Object amount = null;
        try {
            amount = query.getSingleResult();
            logger.log(Level.INFO, "Total points" + amount);
            Query query1 = entityManager.createQuery("select avg(p.x) from Point p");
            amount = query1.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Average X" + amount);

    }

    private void getPoints(EntityManager entityManager) {
        TypedQuery<Point> typedQuery = entityManager.createQuery("select o from Point o", Point.class);
        List<Point> pointList = typedQuery.getResultList();
        Logger logger = Logger.getLogger(Main.class.getName());
        for (Point p : pointList
        ) {
            logger.log(Level.INFO, p.toString());
        }

    }

    private Point getPointbyId(int id, EntityManager entityManager) {

        TypedQuery<Point> typedQuery = entityManager.createQuery("select o from Point o where o.id=:id", Point.class);
        typedQuery.setParameter("id", id);
        Point point = null;

        try {
            point = typedQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
        }


        return point;
    }


}

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("testPersistenceUnit");
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        try {
            for (int i = 0; i < 100; i++) {
                Point point=new Point();
                point.setX(i);
                point.setY(i);
                entityManager.persist(point);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        finally {
            Main main=new Main();
            main.getPoints(entityManager);
            entityManager.close();

        }

    }

    private void getPoints(EntityManager entityManager)
    {
        TypedQuery<Point> typedQuery=entityManager.createQuery("select o from Point o",Point.class);
        List<Point> pointList=typedQuery.getResultList();
        Logger logger=Logger.getLogger(Main.class.getName());
        for (Point p:pointList
             ) {
            logger.log(Level.INFO,p.toString());
        }

    }
}

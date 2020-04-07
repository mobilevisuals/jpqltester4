import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@NamedQueries(
        {
                @NamedQuery(name = "findall", query = "select p from Point p"),
                @NamedQuery(name = "biggerThanX", query = "select p from Point p where p.x>:x")
        }

)

@Entity
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int x,y;

    public Point() {
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

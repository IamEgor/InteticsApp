package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 16.09.2015.
 */
@Entity
public class Car extends Model implements Updatable{

    @Id
    @GeneratedValue
    public Long id;

    @Required
    public String make;

    @Required
    public String model;

    @Required
    public int year;

    @Required
    public Long status;

    public static Model.Finder<Long, Car> finder = new Model.Finder<Long, Car>(Long.class, Car.class);

    @ManyToOne(cascade = CascadeType.ALL)
    public User user;
    @ManyToOne(cascade = CascadeType.ALL)
    public UnregUser unregUser;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    public List<Orders> ordersList = new ArrayList<Orders>();;

    @Override
    public void updateFields(Model model) {
        Car oldCar = (Car) model;
        this.make = oldCar.make;
        this.model = oldCar.model;
        this.year = oldCar.year;
        this.status = oldCar.status;
    }

    @Override
    public String toString() {
        return " Car.class : " +
                " id = " + id +
                " make = " + make +
                " model = " + model +
                " year = " + year +
                " status = " + status;
    }

    public static Long getParentId(Long childId){
        return Car.finder.byId(childId).user.id;
    }

    public static boolean hasSubfields(long carId){

        List<Orders> orders = finder.byId(carId).ordersList;

        if(orders == null)
            return false;
        else
        if(orders.size() > 0)
            return true;
        else
            return false;
    }
}

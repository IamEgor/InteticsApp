package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
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
    public int vin;

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
        this.vin = oldCar.vin;
    }

    @Override
    public String toString() {
        return " Car.class : " +
                " id = " + id +
                " make = " + make +
                " model = " + model +
                " year = " + year +
                " status = " + vin;
    }
}

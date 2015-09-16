package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.util.Date;

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

    @Override
    public void updateFields(Model model) {
        Car oldCar = (Car) model;
        this.make = oldCar.make;
        this.model = oldCar.model;
        this.year = oldCar.year;
        this.vin = oldCar.vin;
    }
}

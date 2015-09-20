package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Egor on 16.09.2015.
 */
@Entity
public class Orders extends Model implements Updatable{

    @Id
    @GeneratedValue
    public Long id;

    @Required
    public Date date;

    @Required
    @Constraints.Min(value = 0)
    @Constraints.Max(value = 10000)
    public int amount;

    public ContactType status;

    public static Model.Finder<Long, Orders> finder = new Model.Finder<Long, Orders>(Long.class, Orders.class);

    @ManyToOne(cascade = CascadeType.ALL)
    public Car car;

    @Override
    public void updateFields(Model model) {
        Orders oldOrder = (Orders) model;
        this.date = oldOrder.date;
        this.amount = oldOrder.amount;
        this.status = oldOrder.status;
    }

    @Override
    public String toString() {
        return " Orders.class : " +
                " id = " + id +
                " date = " + date +
                " amount = " + amount +
                " status = " + status;
    }

    public static Map<String, String> options(){
        LinkedHashMap<String, String> vals = new LinkedHashMap<String, String>();
        for (ContactType cType: ContactType.values()) {
            vals.put(cType.name(), cType.name());
        }
        return vals;
    }

    public enum ContactType {
        Completed(0),
        inProgress(1),
        Cancelled(2);

        public final int id;

        ContactType(int id) {
            this.id = id;
        }
    }
}

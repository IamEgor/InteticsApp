package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 17.09.2015.
 */
@Entity
public class UnregUser extends AbstractUser {

    @Id
    @GeneratedValue
    public Long id;

    @Required
    public String firstName;

    @Required
    public String lastName;

    public static Model.Finder<Long, UnregUser> finder = new Model.Finder<Long, UnregUser>(Long.class, UnregUser.class);


    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unregUser")
    public List<Car> carsList = new ArrayList<Car>();




    @Override
    public void updateFields(Model model) {

        UnregUser oldUser = (UnregUser) model;

        this.firstName = oldUser.firstName;
        this.lastName = oldUser.lastName;
    }



}

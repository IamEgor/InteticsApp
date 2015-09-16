package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Egor on 16.09.2015.
 */
@Entity
public class User extends Model implements Updatable{

    @Id
    @GeneratedValue
    public Long id;

    //@Required
    public String firstName;

    @Required
    public String lastName;

    @Required
    public String birthDate;

    @Required
    public String address;

    public int phone;

    @Email
    public String email;

    public static Model.Finder<Long, User> finder = new Model.Finder<Long, User>(Long.class, User.class);

    @Override
    public void updateFields(Model model) {
        User oldUser = (User) model;
        this.firstName = oldUser.firstName;
        this.lastName = oldUser.lastName;
        this.birthDate = oldUser.birthDate;
        this.address = oldUser.address;
        this.phone = oldUser.phone;
        this.email = oldUser.email;
    }
}

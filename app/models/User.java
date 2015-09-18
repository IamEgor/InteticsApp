package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 16.09.2015.
 */
@Entity
public class User extends AbstractUser {

    @Id
    @GeneratedValue
    public Long id;

    @Required
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<Car> carsList = new ArrayList<Car>();



    public List<String[]> getUserName(){

        ArrayList<String[]> list;

        List<User> users = finder.all();
        list  = new ArrayList<>(users.size());

        for(User user : users)
            list.add(new String[]{user.firstName, user.lastName});

        return list;
    }
    public static List<String> getUserFirstName(){

        ArrayList<String> list;

        List<User> users = finder.all();
        list  = new ArrayList<>(users.size());

        for(User user : users)
            list.add(user.firstName);

        return list;
    }

    public static boolean isUserExist(String firstName, String lastName){

        List<User> tasks = finder.where()
                .ilike("firstName", "%" + firstName + "%")
                .ilike("lastName", "%" + lastName + "%")
                .findList();

        if(tasks == null)
            return false;
        else
            if(tasks.size() > 0)
                return true;
            else
                return false;
    }

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

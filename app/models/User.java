package models;

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
public class User extends Model implements Updatable {

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

    public Long phone;

    @Email
    public String email;

    public static Model.Finder<Long, User> finder = new Model.Finder<Long, User>(Long.class, User.class);

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user")
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

    public static long isUserExist(String firstName, String lastName){

        long id = -1;

        List<User> users = finder.where()
                .ilike("firstName", "%" + firstName + "%")
                .ilike("lastName", "%" + lastName + "%")
                .findList();

        if(users != null)
            if(users.size() > 0)
                id = users.get(0).id;

        return id;
    }

    public static boolean hasSubfields(long userId){

        List<Car> cars = finder.byId(userId).carsList;

        if(cars == null)
            return false;
        else
        if(cars.size() > 0)
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

package models;

import play.db.ebean.Model;

import javax.persistence.MappedSuperclass;
import java.util.List;


/**
 * Created by Egor on 17.09.2015.
 */
@MappedSuperclass
public abstract class AbstractUser extends Model implements Updatable{

    public static Model.Finder<Long, AbstractUser> finder = new Model.Finder<Long, AbstractUser>(Long.class, AbstractUser.class);

}

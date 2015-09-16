package models;

import play.db.ebean.Model;

/**
 * Created by Egor on 17.09.2015.
 */
public interface Updatable {
    public void updateFields(Model model);
}

package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import java.util.List;

/**
 * Created by Egor on 17.09.2015.
 */
public class UsersActions extends Controller{

    private static final Class clazz = User.class;
    private static final Html s = views.html.edit_pages.edit_user.render(Form.form(User.class));

    public static Result getUsers() {

        List<User> users = User.finder.all();
        return ok(views.html.all_fields.users.render(users));
    }

    public static Result newUser() {

        Form<User> userForm = Form.form(clazz);
        return ok(views.html.edit_pages.edit_user.render(userForm));
    }

    public static Result saveUser() {

        Form<User> userForm = Form.form(clazz).bindFromRequest();

        if(userForm.hasErrors())
            return badRequest(views.html.edit_pages.edit_user.render(userForm));
        else {
            User user = userForm.get();

            if (user.id != null) {
                User existingUser = User.finder.byId(user.id);
                existingUser.updateFields(user);
                user = existingUser;
            }

            user.save();

            return redirect(routes.UsersActions.getUsers());
        }
    }

    public static Result editUser(Long id) {

        User row = User.finder.byId(id);
        Form<User> userForm = Form.form(clazz).fill(row);
        return ok(views.html.edit_pages.edit_user.render(userForm));
    }

    public static Result deleteUser(Long id) {
        User.finder.byId(id).delete();
        return redirect(routes.UsersActions.getUsers());
    }

}

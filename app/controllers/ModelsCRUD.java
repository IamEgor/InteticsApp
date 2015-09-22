package controllers;

import models.AbstractUser;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

/**
 * Created by Egor on 17.09.2015.
 */
public class ModelsCRUD<T extends AbstractUser> extends Controller{


    private static final Class clazz = Users.class;
    private static final Html s = views.html.edit_pages.edit_user.render(Form.form(Users.class));
    /*
    public static <T> Result getUsers() {
        List<T> users = T.finder.all();
        return ok(views.html.all_fields.users.render(users));
    }
    */
    public static Result newUser() {

        Form<Users> userForm = Form.form(clazz);
        return ok(views.html.edit_pages.edit_user.render(userForm));
    }

    public static Result saveUser() {

        Form<Users> userForm = Form.form(clazz).bindFromRequest();

        if(userForm.hasErrors())
            return badRequest(views.html.edit_pages.edit_user.render(userForm));
        else {
            Users user = userForm.get();

            if (user.id != null) {
                Users existingUser = Users.finder.byId(user.id);
                existingUser.updateFields(user);
                user = existingUser;
            }

            user.save();

            return redirect(routes.UsersActions.getUsers());
        }
    }

    public static Result editUser(Long id) {

        Users row = Users.finder.byId(id);
        Form<Users> userForm = Form.form(clazz).fill(row);
        return ok(views.html.edit_pages.edit_user.render(userForm));
    }

    public static Result deleteUser(Long id) {
        Users.finder.byId(id).delete();
        return redirect(routes.UsersActions.getUsers());
    }
}

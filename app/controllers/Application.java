package controllers;

import models.Car;
import models.Orders;
import models.User;
import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.Logger;

import views.html.*;

import java.util.List;
import java.util.Map;

public class Application extends Controller {

    public static Result index() {
        //return ok(index.render("Your new application is ready."));

        Form<User> userForm = Form.form(User.class);
        return ok(views.html.edit_unreg_user.render(userForm));
    }

    public static Result saveNew(User ss) {

        return ok();
    }
    public static Result isUserExist() {

        Map<String, String[]> values = request().body().asFormUrlEncoded();
        String firstName = values.get("firstName")[0];
        String lastName = values.get("lastName")[0];

        if (!User.isUserExist(firstName, lastName)) {
            return redirect(routes.Application.newUser(firstName, lastName));
        }


        return redirect(routes.Application.getUserCars());

    }

    public static Result newUser(String firstName, String lastName) {


        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;

        Form<User> userForm = Form.form(User.class).fill(user);

        return ok(views.html.noSuchUser.render(userForm, "User"));
    }

    public static Result saveNewUser(){

        Form<User> userForm = Form.form(User.class).bindFromRequest();

        if(userForm.hasErrors())
            return badRequest(views.html.noSuchUser.render(userForm, "User"));
        else {
            User user = userForm.get();
            user.save();

            return redirect(routes.Application.getUserCars());
        }
    }


    public static Result getUserCars() {

        List<Car> cars = Car.finder.all();

        return ok(views.html.ifCarExist.render(cars));
    }

    public static Result postChosenCar() {

        Map<String, String[]> values = request().body().asFormUrlEncoded();
        String[] car = values.get("car");

        Logger.info("car - " + car[0]);

        return ok();
    }

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",

                        controllers.routes.javascript.UsersActions.getUsers(),
                        controllers.routes.javascript.UsersActions.newUser(),
                        controllers.routes.javascript.UsersActions.saveUser(),
                        controllers.routes.javascript.UsersActions.editUser(),
                        controllers.routes.javascript.UsersActions.deleteUser(),

                        controllers.routes.javascript.CarsActions.getCars(),
                        controllers.routes.javascript.CarsActions.newCar(),
                        controllers.routes.javascript.CarsActions.saveCar(),
                        controllers.routes.javascript.CarsActions.editCar(),
                        controllers.routes.javascript.CarsActions.deleteCar(),

                        controllers.routes.javascript.OrdersActions.getOrders(),
                        controllers.routes.javascript.OrdersActions.newOrder(),
                        controllers.routes.javascript.OrdersActions.saveOrder(),
                        controllers.routes.javascript.OrdersActions.editOrder(),
                        controllers.routes.javascript.OrdersActions.deleteOrder()

                )
        );
    }

}

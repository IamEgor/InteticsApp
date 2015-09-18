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



    public static Result isUserExist() {

        DynamicForm requestData = Form.form().bindFromRequest();

        Map<String, String[]> values = request().body().asFormUrlEncoded();
        String[] firstName = values.get("firstName");
        String[] lastName = values.get("lastName");

            String s = "Don't exist";
            if(User.isUserExist(firstName[0], lastName[0])){
                s = "exist";
            }



            return redirect(routes.Application.test(s));

    }


    public static Result test(String s) {
        //return ok(index.render("Your new application is ready."));

        List<Car> cars = Car.finder.all();

        return ok(views.html.ifCarExist.render(cars));
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

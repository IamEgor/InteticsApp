package controllers;

import models.Car;
import models.Orders;
import models.Users;
import play.Logger;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application extends Controller {

    public static Result index() {
        //return ok(index.render("Your new application is ready."));

        Form<Users> userForm = Form.form(Users.class);
        return ok(views.html.first_page.render(userForm));
    }



    public static Result newUser(String firstName, String lastName) {


        Users user = new Users();
        user.firstName = firstName;
        user.lastName = lastName;

        Form<Users> userForm = Form.form(Users.class).fill(user);

        return ok(views.html.noSuchUser.render(userForm));
    }




    public static Result getUserCars(long usersId) {
        Logger.debug("getUserCars() - usersId = " + usersId);
        List<Car> cars = new ArrayList<>();
        if(usersId != -1){
            cars = Users.finder.byId(usersId).carsList;

        for(Car car : cars)
            Logger.debug(car.toString());}

        return ok(views.html.getUserCars.render(cars, usersId));
    }


    public static Result getCarOrders(Long carId) {

        List<Orders> orders = new ArrayList<>();
        if(carId != -1){
            orders = Car.finder.byId(carId).ordersList;

            for(Orders order : orders)
                Logger.debug(order.toString());}

        return ok(views.html.getCarOrders.render(orders, carId));
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

    public static Result isUserExist() {

        Form<Users> userForm = Form.form(Users.class).bindFromRequest();

        Map<String, String[]> values = request().body().asFormUrlEncoded();
        String firstName = values.get("firstName")[0];
        String lastName = values.get("lastName")[0];

        long usersId = Users.isUserExist(firstName, lastName);

        if (usersId == -1) {
            return redirect(routes.Application.newUser(firstName, lastName));
            //return redirect(routes.UserActions.newUser())
        }


        return redirect(routes.Application.getUserCars(usersId));

    }

    public static Result saveNewUser(){

        Form<Users> userForm = Form.form(Users.class).bindFromRequest();

        if(userForm.hasErrors())
            return badRequest(views.html.noSuchUser.render(userForm));
        else {
            Users user = userForm.get();
            user.save();
            Logger.debug(" user.id - saveNewUser() = " + user.id);
            return redirect(routes.Application.getUserCars(user.id));
        }
    }

    public static Result postChosenCar() {

        Map<String, String[]> values = request().body().asFormUrlEncoded();
        long carId = Long.parseLong(values.get("car")[0]);
        Logger.info("car - " + carId);

        return redirect(routes.Application.getCarOrders(carId));
    }

}

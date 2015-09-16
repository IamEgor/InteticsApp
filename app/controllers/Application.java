package controllers;

import models.Car;
import models.Orders;
import models.User;
import play.data.Form;
import play.mvc.*;
import play.Logger;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }


    public static Result getUsers() {

        List<User> users = User.finder.all();
        return ok(views.html.users.render(users));
    }

    public static Result newUser() {

        Form<User> userForm = Form.form(User.class);
        return ok(views.html.edit_user.render(userForm));
    }

    public static Result saveUser() {

        Form<User> userForm = Form.form(User.class).bindFromRequest();

        if(userForm.hasErrors())
            return badRequest(views.html.edit_user.render(userForm));
        else {
            User user = userForm.get();

            if (user.id != null) {
                User existingUser = User.finder.byId(user.id);
                existingUser.updateFields(user);
                user = existingUser;
            }

            user.save();

            return redirect(routes.Application.getUsers());
        }
    }

    public static Result editUser(Long id) {

        User row = User.finder.byId(id);
        Form<User> userForm = Form.form(User.class).fill(row);
        return ok(views.html.edit_user.render(userForm));
    }

    public static Result deleteUser(Long id) {
        User.finder.byId(id).delete();
        return redirect(routes.Application.getUsers());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Result getOrders() {

        List<Orders> orders = Orders.finder.all();
        return ok(views.html.orders.render(orders));
    }

    public static Result newOrder() {

        Form<Orders> orderForm = Form.form(Orders.class);
        return ok(views.html.edit_order.render(orderForm));
    }

    public static Result saveOrder() {

        Form<Orders> orderForm = Form.form(Orders.class).bindFromRequest();

        if(orderForm.hasErrors())
            return badRequest(views.html.edit_order.render(orderForm));

        Orders order = orderForm.get();

        if (order.id != null){
            Orders existingOrder = Orders.finder.byId(order.id);
            existingOrder.updateFields(order);
            order = existingOrder;
        }

        order.save();

        return redirect(routes.Application.getOrders());
    }

    public static Result editOrder(Long id) {

        Orders row = Orders.finder.byId(id);
        Form<Orders> orderForm = Form.form(Orders.class).fill(row);
        return ok(views.html.edit_order.render(orderForm));
    }

    public static Result deleteOrder(Long id) {
        Orders.finder.byId(id).delete();
        return redirect(routes.Application.getOrders());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Result getCars() {

        List<Car> cars = Car.finder.all();
        return ok(views.html.cars.render(cars));
    }

    public static Result newCar() {

        Form<Car> carForm = Form.form(Car.class);
        return ok(views.html.edit_car.render(carForm));
    }

    public static Result saveCar() {

        Form<Car> carForm = Form.form(Car.class).bindFromRequest();

        if(carForm.hasErrors())
            return badRequest(views.html.edit_car.render(carForm));

        Car car = carForm.get();

        if (car.id != null){
            Car existingOrder = Car.finder.byId(car.id);
            existingOrder.updateFields(car);
            car = existingOrder;
        }

        car.save();

        return redirect(routes.Application.getCars());
    }

    public static Result editCar(Long id) {

        Car car = Car.finder.byId(id);
        Form<Car> carForm = Form.form(Car.class).fill(car);
        return ok(views.html.edit_car.render(carForm));
    }

    public static Result deleteCar(Long id) {
        Car.finder.byId(id).delete();
        return redirect(routes.Application.getCars());
    }

}

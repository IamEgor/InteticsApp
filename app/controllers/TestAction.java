package controllers;

import models.Car;
import models.Orders;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Egor on 17.09.2015.
 */
public class TestAction extends Controller {


    public static Result test() {
        Form<User> userForm = Form.form(User.class);
        Form<Car> carForm = Form.form(Car.class);
        Form<Orders> ordersForm = Form.form(Orders.class);

        return ok(views.html.TEMP.render(userForm, carForm, ordersForm));
    }
}

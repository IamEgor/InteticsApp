package controllers;

import models.Car;
import models.Orders;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Egor on 17.09.2015.
 */
public class TestAction extends Controller {


    public static Result test() {
        Form<Users> userForm = Form.form(Users.class);
        Form<Car> carForm = Form.form(Car.class);
        Form<Orders> ordersForm = Form.form(Orders.class);

        return ok(views.html.TEMP.render(userForm, carForm, ordersForm));
    }
}

package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;
import models.Car;
import models.Users;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Egor on 17.09.2015.
 */
public class CarsActions extends Controller {

    public static Result getCars() {

        List<Car> cars = Car.finder.all();
        return ok(views.html.all_fields.cars.render(cars));
    }

    public static Result newCar() {

        Form<Car> carForm = Form.form(Car.class);
        return ok(views.html.edit_pages.edit_car.render(carForm));
    }

    public static Result newCarWithUser(long usersId) {

        Form<Car> carForm = Form.form(Car.class);
        return ok(views.html.edit_pages.edit_car_with_user.render(carForm, usersId));
    }

    public static Result saveCarWithUser(long usersId) {

        Form<Car> carForm = Form.form(Car.class).bindFromRequest();

        play.Logger.debug(carForm.errorsAsJson().textValue());

        if(carForm.hasErrors())
            return badRequest(views.html.edit_pages.edit_car_with_user.render(carForm, usersId));

        Car car = carForm.get();

        car.user = Users.finder.byId(usersId);
        play.Logger.debug(car.toString());
        car.save();

        return redirect(routes.Application.getUserCars(usersId));
    }

    public static Result saveCar() {

        Form<Car> carForm = Form.form(Car.class).bindFromRequest();

        if(carForm.hasErrors())
            return badRequest(views.html.edit_pages.edit_car.render(carForm));

        Car car = carForm.get();

        if (car.id != null){
            Car existingOrder = Car.finder.byId(car.id);
            existingOrder.updateFields(car);
            car = existingOrder;
        }

        car.save();

        //return redirect(routes.CarsActions.getCars());
        return redirect(routes.Application.getUserCars(car.user.id));
    }

    public static Result editCar(Long carId) {

        Car car = Car.finder.byId(carId);
        Form<Car> carForm = Form.form(Car.class).fill(car);
        return ok(views.html.edit_pages.edit_car.render(carForm));
    }

    public static Result deleteCar(Long carId) {

        long userId = Car.finder.byId(carId).user.id;

        Update<Car> upd = Ebean.createUpdate(Car.class, "DELETE Car WHERE id=:id");
        upd.set("id", carId.toString());
        upd.execute();

        return redirect(routes.Application.getUserCars(userId));
    }

}

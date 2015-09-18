package controllers;

import models.Car;
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

        return redirect(routes.CarsActions.getCars());
    }

    public static Result editCar(Long id) {

        Car car = Car.finder.byId(id);
        Form<Car> carForm = Form.form(Car.class).fill(car);
        return ok(views.html.edit_pages.edit_car.render(carForm));
    }

    public static Result deleteCar(Long id) {
        Car.finder.byId(id).delete();
        return redirect(routes.CarsActions.getCars());
    }

}

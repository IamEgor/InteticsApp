package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;
import models.Car;
import models.Orders;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Egor on 17.09.2015.
 */
public class OrdersActions extends Controller {

    public static Result getOrders() {

        List<Orders> orders = Orders.finder.all();
        return ok(views.html.all_fields.orders.render(orders));
    }

    public static Result newOrder() {

        Form<Orders> orderForm = Form.form(Orders.class);
        return ok(views.html.edit_pages.edit_order.render(orderForm));
    }
//////////////////////////////////////////////////////////////////////
    public static Result newOrderWithCar(Long carId) {

        Form<Orders> orderForm = Form.form(Orders.class);
        return ok(views.html.edit_pages.edit_order_with_car.render(orderForm, carId));
    }

    public static Result saveCarWithUser(long carId) {

        Form<Orders> orderForm = Form.form(Orders.class).bindFromRequest();


        if (orderForm.hasErrors())
            return badRequest(views.html.edit_pages.edit_order_with_car.render(orderForm, carId));

        Orders order = orderForm.get();

        order.car = Car.finder.byId(carId);
        play.Logger.debug(order.toString());
        order.save();

        return redirect(routes.Application.getCarOrders(carId));
    }
//////////////////////////////////////////////////////////////////////

    public static Result saveOrder() {

        Form<Orders> orderForm = Form.form(Orders.class).bindFromRequest();

        if(orderForm.hasErrors())
            return badRequest(views.html.edit_pages.edit_order.render(orderForm));

        Orders order = orderForm.get();

        if (order.id != null){
            Orders existingOrder = Orders.finder.byId(order.id);
            existingOrder.updateFields(order);
            order = existingOrder;
        }

        order.save();

        long carId = Orders.finder.byId(order.id).car.id;
        return redirect(routes.Application.getCarOrders(carId));

    }

    public static Result editOrder(Long id) {

        Orders row = Orders.finder.byId(id);
        Form<Orders> orderForm = Form.form(Orders.class).fill(row);
        return ok(views.html.edit_pages.edit_order.render(orderForm));
    }

    public static Result deleteOrder(Long orderId) {

        long carId = Orders.finder.byId(orderId).car.id;

        Update<Car> upd = Ebean.createUpdate(Car.class, "DELETE Orders WHERE id=:id");
        upd.set("id", orderId.toString());
        upd.execute();

        return redirect(routes.Application.getCarOrders(carId));
    }

}

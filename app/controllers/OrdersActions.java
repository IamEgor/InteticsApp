package controllers;

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

        return redirect(routes.OrdersActions.getOrders());
    }

    public static Result editOrder(Long id) {

        Orders row = Orders.finder.byId(id);
        Form<Orders> orderForm = Form.form(Orders.class).fill(row);
        return ok(views.html.edit_pages.edit_order.render(orderForm));
    }

    public static Result deleteOrder(Long id) {
        Orders.finder.byId(id).delete();
        return redirect(routes.OrdersActions.getOrders());
    }

}

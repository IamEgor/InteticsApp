import play.GlobalSettings;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.notFound;

/**
 * Created by Egor on 20.09.2015.
 */
public class Global extends GlobalSettings {

    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader requestHeader) {
        return F.Promise.promise(
                new F.Function0<Result>() {
                    public Result apply() {
                        return notFound(views.html.NOT_FOUND_PAGE.render());
                    }
                }
        );
    }
}

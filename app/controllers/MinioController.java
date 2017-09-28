package controllers;

import com.eclipsesource.json.JsonObject;
import play.mvc.Controller;

public class MinioController extends Controller {

    public JsonObject getUnknownErrorResult() {
        return new JsonObject()
                .add("code", 500)
                .add("response", "Something went wrong !!");
    }
}

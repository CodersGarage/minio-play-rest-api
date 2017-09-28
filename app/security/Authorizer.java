package security;

import com.eclipsesource.json.JsonObject;
import configs.MinioConfig;
import configs.ParamConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * := Coded with love by Sakib Sami on 8/7/17.
 * := minio_play_rest_api
 * := root@sakib.ninja
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class Authorizer extends Security.Authenticator {
    private OkHttpClient okHttpClient;

    public Authorizer() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Http.Request request = ctx.request();
        String userHashCode = request.getHeader(ParamConfig.PARAM_USER_HASH_CODE_KEY);
        String accessToken = request.getHeader(ParamConfig.PARAM_ACCESS_TOKEN_KEY);
        if (userHashCode == null || accessToken == null) {
            return null;
        }

        Request httpRequest = new Request.Builder()
                .addHeader(ParamConfig.PARAM_ACCESS_TOKEN_KEY, accessToken)
                .addHeader(ParamConfig.PARAM_USER_HASH_CODE_KEY, userHashCode)
                .url(MinioConfig.getAuthUrl())
                .build();

        try {
            Response response = okHttpClient.newCall(httpRequest).execute();
            JsonObject result = com.eclipsesource.json.Json.parse(response.body().string()).asObject();
            if (result.getInt("code", 0) == ParamConfig.RESULT_SUCCESS) {
                return userHashCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        JsonObject result = new JsonObject();
        result.add("code", ParamConfig.RESULT_UNAUTHORIZED);
        result.add("response", "Unauthorized request.");
        return ok(Json.parse(result.toString()));
    }
}

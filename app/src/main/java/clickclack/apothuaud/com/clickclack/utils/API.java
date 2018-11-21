package clickclack.apothuaud.com.clickclack.utils;

import android.util.Log;

public class API {

    @SuppressWarnings("ConstantConditions")
    public static String getUri() {
        String URI = null;
        switch (Env.getEnv()) {
            case "rec":
                URI = "https://rec-clickclack-api.herokuapp.com";
                break;
            case "dev":
                URI = "http://localhost:5000";
                break;
            case "prod":
                URI = "https://clickclack-api.herokuapp.com";
                break;
            default:
                Log.e("Env", "Environnement set is not handled: " + Env.getEnv());
                break;
        }
        return URI;
    }
}

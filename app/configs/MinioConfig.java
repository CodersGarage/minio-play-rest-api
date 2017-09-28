package configs;

import play.Configuration;
import play.Environment;

import java.util.UUID;

/**
 * := Coded with love by Sakib Sami on 7/25/17.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class MinioConfig {
    private static Configuration configuration = Configuration.load(Environment.simple());

    public static String getApiHost() {
        return configuration.getString("minio_config.api_host");
    }

    public static String getMsUri() {
        return configuration.getString("minio_config.ms_uri");
    }

    public static String getMsSecret() {
        return configuration.getString("minio_config.ms_secrete");
    }

    public static String getMsAccessKey() {
        return configuration.getString("minio_config.ms_accessToken");
    }

    public static String getRandomText() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getAuthUrl() {
        return configuration.getString("auth.url");
    }
}

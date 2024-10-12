package eg.amazon.utils;


import com.github.javafaker.Faker;

import java.util.Properties;
import java.util.Random;

public class ConfigUtils {
    private Properties properties;
    private static ConfigUtils configUtils;
    private static final Random random = new Random();
    private static final Faker faker = new Faker();


    private ConfigUtils() {
        String env = System.getProperty("env", "PRODUCTION");
        switch (env) {
            case "TESTING" ->
                    properties = PropertiesUtils.loadProperties("src/test/java/eg/amazon/config/testing.properties");
            case "PRODUCTION" ->
                    properties = PropertiesUtils.loadProperties("src/test/java/eg/amazon/config/production.properties");
            default -> throw new RuntimeException("Environment isn't supported");
        }
    }

    public static ConfigUtils getInstance() {
        if (configUtils == null) {
            configUtils = new ConfigUtils();
        }
        return configUtils;
    }

    public String getBaseUrl() {
        String prop = properties.getProperty("baseUrl");
        if (prop != null) return prop;
        throw new RuntimeException("Couldn't find the base url in the property file");
    }

    public String getEmail() {
        String prop = properties.getProperty("email");
        if (prop != null) return prop;
        throw new RuntimeException("Couldn't find the Email in the property file");
    }

    public String getPassword() {
        String prop = properties.getProperty("password");
        if (prop != null) return prop;
        throw new RuntimeException("Couldn't find the password in the property file");
    }
    public String getAPIBaseUrl() {
        String prop = properties.getProperty("api_baseUrl");
        if (prop != null) return prop;
        throw new RuntimeException("Couldn't find the base url in the property file");
    }

    public String getAPIEmail() {
        String prop = properties.getProperty("api_email");
        if (prop != null) return prop;
        throw new RuntimeException("Couldn't find the Email in the property file");
    }

    public String getAPIPassword() {
        String prop = properties.getProperty("api_password");
        if (prop != null) return prop;
        throw new RuntimeException("Couldn't find the password in the property file");
    }

    public static String generateRandomMobileNumber() {
        String[] validThirdDigits = {"0", "1", "2", "5"};
        String secondDigit = validThirdDigits[random.nextInt(validThirdDigits.length)];
        StringBuilder mobileNumber = new StringBuilder("1" + secondDigit);

        for (int i = 0; i < 8; i++) {
            mobileNumber.append(faker.number().digit());
        }

        return mobileNumber.toString();
    }

}

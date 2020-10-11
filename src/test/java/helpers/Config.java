package helpers;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Resource.Classpath("test.properties")
public class Config {

    private static Config config = new Config();

    @Property("api.url")
    private static String apiUrl;
    @Property("api.user")
    private static String apiUser;
    @Property("api.user.token")
    private static String apiUserToken;

    private Config() {
        PropertyLoader.populate(this);
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public static String getApiUrl() {
        return apiUrl;
    }

    public static String getApiUser() {
        return apiUser;
    }

    public static String getApiUserToken() {
        return apiUserToken;
    }
}

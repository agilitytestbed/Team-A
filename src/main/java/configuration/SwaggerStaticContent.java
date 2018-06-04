package configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
public class SwaggerStaticContent {

    //    Session model
    public static final String SESSION_ID = "id of the session";
    public static final String SESSION_TRANSACTIONs = "transactions belonging to the session";
    public static final String SESSION_TOKEN = "authorization token for a session";

}

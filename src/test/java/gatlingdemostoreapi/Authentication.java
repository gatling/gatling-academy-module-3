package gatlingdemostoreapi;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.jmesPath;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Authentication {
    public static ChainBuilder authenticate =
            doIf(session -> !session.getBoolean("authenticated")).then(
                    exec(http("Authenticate")
                            .post("/api/authenticate")
                            .body(StringBody("{\"username\": \"admin\",\"password\": \"admin\"}"))
                            .check(status().is(200))
                            .check(jmesPath("token").saveAs("jwt")))
                            .exec(session -> session.set("authenticated", true)));
}

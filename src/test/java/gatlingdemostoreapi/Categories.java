package gatlingdemostoreapi;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.jmesPath;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Categories {

    public static FeederBuilder.Batchable<String> categoriesFeeder =
            csv("data/categories.csv").random();

    public static ChainBuilder list =
            exec(http("List categories")
                    .get("/api/category")
                    .check(jmesPath("[? id == `6`].name").ofList().is(List.of("For Her"))));

    public static ChainBuilder update =
            feed(categoriesFeeder)
                    .exec(Authentication.authenticate)
                    .exec(http("Update category")
                            .put("/api/category/#{categoryId}")
                            .headers(Headers.authorizationHeaders)
                            .body(StringBody("{\"name\": \"#{categoryName}\"}"))
                            .check(jmesPath("name").isEL("#{categoryName}")));
}

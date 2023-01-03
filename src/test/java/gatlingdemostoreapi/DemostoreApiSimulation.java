package gatlingdemostoreapi;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class DemostoreApiSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://demostore.gatling.io")
    .header("Cache-Control", "no-cache")
    .contentTypeHeader("application/json")
    .acceptHeader("application/json");

  
  private static Map<CharSequence, String> authorizationHeaders = Map.ofEntries(
    Map.entry("authorization", "Bearer #{jwt}")
  );

  private static ChainBuilder initSession = exec(session -> session.set("authenticated", false));

  private static class Authentication {
    private static ChainBuilder authenticate =
      doIf(session -> !session.getBoolean("authenticated")).then(
              exec(http("Authenticate")
                      .post("/api/authenticate")
                      .body(StringBody("{\"username\": \"admin\",\"password\": \"admin\"}"))
                      .check(status().is(200))
                      .check(jmesPath("token").saveAs("jwt")))
                      .exec(session -> session.set("authenticated", true)));
  }

  private static class Categories {
    private static ChainBuilder list =
        exec(http("List categories")
          .get("/api/category")
          .check(jmesPath("[? id == `6`].name").ofList().is(List.of("For Her"))));

    private static ChainBuilder update =
        exec(http("Update category")
          .put("/api/category/7")
          .headers(authorizationHeaders)
          .body(StringBody("{\"name\": \"Everyone\"}"))
          .check(jmesPath("name").is("Everyone")));
  }

  private static class Products {
    private static ChainBuilder list =
        exec(http("List products")
          .get("/api/product?category=7")
          .check(jmesPath("[? categoryId != '7']").ofList().is(Collections.emptyList())));

    private static ChainBuilder get =
        exec(http("Get product")
          .get("/api/product/34")
          .check(jmesPath("id").ofInt().is(34)));

    private static ChainBuilder update =
       exec(http("Update product")
          .put("/api/product/34")
          .headers(authorizationHeaders)
          .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/update-product.json"))
          .check(jmesPath("price").is("15.99")));

    private static ChainBuilder create =
       repeat(3, "productCount").on(
         exec(http("Create product #{productCount}")
                 .post("/api/product")
                 .headers(authorizationHeaders)
                 .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product#{productCount}.json")))
       );
  }


  private ScenarioBuilder scn = scenario("DemostoreApiSimulation")
    .exec(initSession)
    .exec(Categories.list)
    .pause(2)
    .exec(Products.list)
    .pause(2)
    .exec(Products.get)
    .pause(2)
    .exec(Authentication.authenticate)
    .pause(2)
    .exec(Products.update)
    .pause(2)
    .exec(Products.create)
    .pause(2)
    .exec(Categories.update);

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

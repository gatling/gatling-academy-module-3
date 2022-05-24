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

  
  private Map<CharSequence, String> authorizationHeaders = Map.ofEntries(
    Map.entry("authorization", "Bearer #{jwt}")
  );


  private ScenarioBuilder scn = scenario("DemostoreApiSimulation")
    .exec(
      http("List categories")
        .get("/api/category")
    )
    .pause(2)
    .exec(
      http("List products")
        .get("/api/product?category=7")
    )
    .pause(2)
    .exec(
      http("Get product")
        .get("/api/product/34")
    )
    .pause(2)
    .exec(
      http("Authenticate")
        .post("/api/authenticate")
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/authenticate-admin.json"))
        .check(jsonPath("$.token").saveAs("jwt"))
    )
    .pause(2)
    .exec(
      http("Update product")
        .put("/api/product/34")
        .headers(authorizationHeaders)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/update-product.json")))
    .pause(2)
    .exec(
      http("Create product 1")
        .post("/api/product")
        .headers(authorizationHeaders)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product1.json"))
    )
    .pause(2)
    .exec(
      http("Create product 2")
        .post("/api/product")
        .headers(authorizationHeaders)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product2.json"))
    )
    .pause(2)
    .exec(
      http("Create product 3")
        .post("/api/product")
        .headers(authorizationHeaders)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product3.json"))
    )
    .pause(2)
    .exec(
      http("Update category")
        .put("/api/category/7")
        .headers(authorizationHeaders)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/update-category.json"))
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

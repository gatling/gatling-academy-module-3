package gatlingdemostoreapi;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class DemostoreApiSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://demostore.gatling.io")
    .inferHtmlResources(AllowList(), DenyList())
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("PostmanRuntime/7.26.8");
  
  private Map<CharSequence, String> headers_0 = Map.of("Cache-Control", "no-cache");
  
  private Map<CharSequence, String> headers_3 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json")
  );
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("authorization", "Bearer #{jwt}")
  );


  private ScenarioBuilder scn = scenario("DemostoreApiSimulation")
    .exec(
      http("request_0")
        .get("/api/category")
        .headers(headers_0)
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0000_response.json")))
    )
    .pause(2)
    .exec(
      http("request_1")
        .get("/api/product?category=7")
        .headers(headers_0)
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0001_response.json")))
    )
    .pause(2)
    .exec(
      http("request_2")
        .get("/api/product/34")
        .headers(headers_0)
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0002_response.json")))
    )
    .pause(2)
    .exec(
      http("request_3")
        .post("/api/authenticate")
        .headers(headers_3)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0003_request.json"))
        .check(jsonPath("$.token").saveAs("jwt"))
    )
    .pause(2)
    .exec(
      http("request_4")
        .put("/api/product/34")
        .headers(headers_4)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0004_request.json")))
    .pause(2)
    .exec(
      http("request_5")
        .post("/api/product")
        .headers(headers_4)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0005_request.json"))
    )
    .pause(2)
    .exec(
      http("request_6")
        .post("/api/product")
        .headers(headers_4)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0006_request.json"))
    )
    .pause(2)
    .exec(
      http("request_7")
        .post("/api/product")
        .headers(headers_4)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0007_request.json"))
    )
    .pause(2)
    .exec(
      http("request_8")
        .put("/api/category/7")
        .headers(headers_4)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0008_request.json"))
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

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
    .baseUrl("http://demostore.gatling.io")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-GB,en;q=0.9")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36");
  
  private Map<CharSequence, String> headers_2 = Map.of("accept", "application/json");
  
  private Map<CharSequence, String> headers_13 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "http://demostore.gatling.io"),
    Map.entry("accept", "application/json")
  );
  
  private Map<CharSequence, String> headers_17 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "http://demostore.gatling.io"),
    Map.entry("accept", "application/json"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY1MzU1NjA4NywiZXhwIjoxNjUzNTU5Njg3fQ.pSwG81i0B4s-5OEvYZTpzKqQLF_P2lbqG1-QdIvqD34")
  );


  private ScenarioBuilder scn = scenario("DemostoreApiSimulation")
    .exec(
      http("request_0")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0000_response.png")))
    )
    .pause(3)
    .exec(
      http("request_1")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0001_response.png")))
    )
    .pause(5)
    .exec(
      http("request_2")
        .get("/api/category")
        .headers(headers_2)
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0002_response.json")))
    )
    .pause(4)
    .exec(
      http("request_3")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0003_response.png")))
    )
    .pause(5)
    .exec(
      http("request_4")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0004_response.png")))
    )
    .pause(1)
    .exec(
      http("request_5")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0005_response.png")))
    )
    .pause(4)
    .exec(
      http("request_6")
        .get("/api/product?category=7")
        .headers(headers_2)
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0006_response.json")))
    )
    .pause(7)
    .exec(
      http("request_7")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0007_response.png")))
    )
    .pause(1)
    .exec(
      http("request_8")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0008_response.png")))
    )
    .pause(2)
    .exec(
      http("request_9")
        .get("/api/product/34")
        .headers(headers_2)
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0009_response.json")))
    )
    .pause(5)
    .exec(
      http("request_10")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0010_response.png")))
    )
    .pause(3)
    .exec(
      http("request_11")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0011_response.png")))
    )
    .pause(2)
    .exec(
      http("request_12")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0012_response.png")))
    )
    .pause(4)
    .exec(
      http("request_13")
        .post("/api/authenticate")
        .headers(headers_13)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0013_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0013_response.json")))
    )
    .pause(8)
    .exec(
      http("request_14")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0014_response.png")))
    )
    .pause(11)
    .exec(
      http("request_15")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0015_response.png")))
    )
    .pause(2)
    .exec(
      http("request_16")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0016_response.png")))
    )
    .pause(12)
    .exec(
      http("request_17")
        .put("/api/product/34")
        .headers(headers_17)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0017_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0017_response.json")))
    )
    .pause(6)
    .exec(
      http("request_18")
        .put("/api/product/34")
        .headers(headers_17)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0018_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0018_response.json")))
    )
    .pause(10)
    .exec(
      http("request_19")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0019_response.png")))
    )
    .pause(4)
    .exec(
      http("request_20")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0020_response.png")))
    )
    .pause(8)
    .exec(
      http("request_21")
        .post("/api/product")
        .headers(headers_17)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0021_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0021_response.json")))
    )
    .pause(24)
    .exec(
      http("request_22")
        .post("/api/product")
        .headers(headers_17)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0022_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0022_response.json")))
    )
    .pause(21)
    .exec(
      http("request_23")
        .post("/api/product")
        .headers(headers_17)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0023_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0023_response.json")))
    )
    .pause(5)
    .exec(
      http("request_24")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0024_response.png")))
    )
    .pause(1)
    .exec(
      http("request_25")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0025_response.png")))
    )
    .pause(1)
    .exec(
      http("request_26")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0026_response.png")))
    )
    .pause(9)
    .exec(
      http("request_27")
        .put("/api/category/7")
        .headers(headers_17)
        .body(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0027_request.json"))
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0027_response.json")))
    )
    .pause(6)
    .exec(
      http("request_28")
        .get("/swagger-ui/favicon-32x32.png?v=3.0.4")
        .check(bodyBytes().is(RawFileBody("gatlingdemostoreapi/demostoreapisimulation/0028_response.png")))
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

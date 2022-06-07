package gatlingdemostoreapi;

import java.time.Duration;
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

  private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "5"));

  private static final Duration RAMP_DURATION =
          Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION", "10")));

  private static final Duration TEST_DURATION =
          Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));

  @Override
  public void before() {
      System.out.printf("Running test with %d users%n", USER_COUNT);
      System.out.printf("Ramping users over %d seconds%n", RAMP_DURATION.getSeconds());
      System.out.printf("Total test duration: %d seconds%n", TEST_DURATION.getSeconds());
  }

  @Override
  public void after() {
      System.out.println("Stress test completed");
  }

  private static ChainBuilder initSession = exec(session -> session.set("authenticated", false));

  private static class UserJourneys {

      private static Duration minPause = Duration.ofMillis(200);
      private static Duration maxPause = Duration.ofSeconds(3);

      public static ChainBuilder admin =
          exec(initSession)
          .exec(Categories.list)
          .pause(minPause, maxPause)
          .exec(Products.list)
          .pause(minPause, maxPause)
          .exec(Products.get)
          .pause(minPause, maxPause)
          .exec(Products.update)
          .pause(minPause, maxPause)
          .repeat(3).on(exec(Products.create))
          .pause(minPause, maxPause)
          .exec(Categories.update);

      public static ChainBuilder priceScrapper =
          exec(Categories.list)
          .pause(minPause, maxPause)
          .exec(Products.listAll);

      public static ChainBuilder priceUpdater =
          exec(initSession)
          .exec(Products.listAll)
          .pause(minPause, maxPause)
          .repeat("#{allProducts.size()}", "productIndex").on(
                  exec(session -> {
                      int index = session.getInt("productIndex");
                      List<Object> allProducts = session.getList("allProducts");
                      return session.set("product", allProducts.get(index));
                  })
          .exec(Products.update)
          .pause(minPause, maxPause));
  }

  private static class Scenarios {
      public static ScenarioBuilder defaultScn = scenario("Default load test")
              .during(TEST_DURATION)
              .on(
                      randomSwitch().on(
                              Choice.withWeight(20d, exec(UserJourneys.admin)),
                              Choice.withWeight(40d, exec(UserJourneys.priceScrapper)),
                              Choice.withWeight(40d, exec(UserJourneys.priceUpdater))
                      )
              );

      public static ScenarioBuilder noAdminsScn = scenario("Load test without admin users")
              .during(Duration.ofSeconds(60))
              .on(
                      randomSwitch().on(
                              Choice.withWeight(60d, exec(UserJourneys.priceScrapper)),
                              Choice.withWeight(40d, exec(UserJourneys.priceUpdater))
                      )
              );
  }

    {
        setUp(
                Scenarios.defaultScn.injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION)),
                Scenarios.noAdminsScn.injectOpen(rampUsers(5).during(Duration.ofSeconds(30))))
                .protocols(httpProtocol);
    }
}

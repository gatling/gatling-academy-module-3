package gatlingdemostoreapi;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Products {

    public static FeederBuilder.Batchable<String> productsFeeder =
            csv("data/products.csv").circular();

    public static ChainBuilder listAll =
            exec(http("List all products")
                    .get("/api/product")
                    .check(jmesPath("[*]").ofList().saveAs("allProducts")));

    public static ChainBuilder list =
            exec(http("List products")
                    .get("/api/product?category=7")
                    .check(jmesPath("[? categoryId != '7']").ofList().is(Collections.emptyList()))
                    .check(jmesPath("[*].id").ofList().saveAs("allProductIds")));

    public static ChainBuilder get =
            exec(session -> {
                List<Integer> allProductIds = session.getList("allProductIds");
                return session.set("productId", allProductIds.get(new Random().nextInt(allProductIds.size())));
            })
                    .exec(http("Get product")
                            .get("/api/product/#{productId}")
                            .check(jmesPath("id").ofInt().isEL("#{productId}"))
                            .check(jmesPath("@").ofMap().saveAs("product")));


    public static ChainBuilder update =
            exec(Authentication.authenticate)
                    .exec( session -> {
                        Map<String, Object> product = session.getMap("product");
                        return session
                                .set("productCategoryId", product.get("categoryId"))
                                .set("productName", product.get("name"))
                                .set("productDescription", product.get("description"))
                                .set("productImage", product.get("image"))
                                .set("productPrice", product.get("price"))
                                .set("productId", product.get("id"));
                    })
                    .exec(http("Update product #{productName}")
                            .put("/api/product/#{productId}")
                            .headers(Headers.authorizationHeaders)
                            .body(ElFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product.json"))
                            .check(jmesPath("price").isEL("#{productPrice}")));

    public static ChainBuilder create =
            exec(Authentication.authenticate)
                    .feed(productsFeeder)
                    .exec(http("Create product #{productName}")
                            .post("/api/product")
                            .headers(Headers.authorizationHeaders)
                            .body(ElFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product.json")));
}

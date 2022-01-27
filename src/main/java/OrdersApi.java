import java.util.Map;
import java.util.HashMap;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrdersApi extends RASpecs{
    private static final String API_ORDERS = "/api/orders";
    private static final Map<String, String> body = new HashMap<>();

    @Step("Создание заказа")
    public Response orderCreation(String ingredientId, String accessToken){
        body.put("ingredients", ingredientId);
        return given()
                .spec(getBaseSpecs())
                .headers("Authorization", accessToken)
                .body(body)
                .when()
                .post(API_ORDERS);
    }

    @Step("Получение заказа")
    public Response getOrder(String accessToken){
        return given()
                .spec(getBaseSpecs())
                .headers("Authorization", accessToken)
                .when()
                .get(API_ORDERS);
    }

    @Step("Создание заказа без ингредиентов")
    public Response orderCreationWithNoIngredients(String accessToken){
        return given()
                .spec(getBaseSpecs())
                .headers("Authorization", accessToken)
                .when()
                .post(API_ORDERS);
    }

    @Step("Получение заказа без авторизации")
    public Response getOrderWithNoAuthorization(){
        return given()
                .spec(getBaseSpecs())
                .when()
                .get(API_ORDERS);
    }

    @Step("Создание заказа без авторизации")
    public Response makeOrderWithNoAuthorization(String ingredientId){
        body.put("ingredients", ingredientId);
        return given()
                .spec(getBaseSpecs())
                .body(body)
                .when()
                .post(API_ORDERS);
    }

}
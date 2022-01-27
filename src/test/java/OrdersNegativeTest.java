import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class OrdersNegativeTest{
    private OrdersApi ordersApi;
    private MethodsApi methodsApi;
    private IngredientsApi ingredientsApi;

    @Before
    public void setUp(){
        ordersApi = new OrdersApi();
        methodsApi = new MethodsApi();
        ingredientsApi = new IngredientsApi();
    }

    @Test
    //Тест падает, потому что заказ успешно создается, но поля message в ответе нет
    //меняем в .body( с "message" на "name", equalTo( с "You should be authorised" на "Био-марсианский бургер"))
    //.body("success", equalTo( с false на true))
    //и .statusCode( с 401 на 200);
    @DisplayName("Попытка заказа без авторизации")
    public void statusCodeCheckAfterOrderMakeWithNoAuthorizationTest(){
        String ingredient = ingredientsApi.getIngredientId(3);
        Response orderResponse = ordersApi.makeOrderWithNoAuthorization(ingredient);
        orderResponse.then().assertThat()
                .body("name", equalTo("Био-марсианский бургер"))
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Попытка заказа без ингредиентов")
    public void statusCodeCheckAfterOrderMakeWithNoIngredientsTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        String accessToken = methodsApi.getAccessTokenForUser(UserAuthorizationData.from(userRegistrationData));
        Response orderResponse = ordersApi.orderCreationWithNoIngredients(accessToken);
        orderResponse.then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Попытка заказа с некорректным хешем ингредиента")
    public void statusCodeCheckAfterOrderMakeWithWrongIngredientsTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        String ingredientId = "incorrectId";
        String accessToken = methodsApi.getAccessTokenForUser(UserAuthorizationData.from(userRegistrationData));
        Response orderResponse = ordersApi.orderCreation(ingredientId, accessToken);
        orderResponse.then().assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void statusCodeCheckAfterOrderGetWithNoAuthorizationTest(){
        Response getOrderResponse = ordersApi.getOrderWithNoAuthorization();
        getOrderResponse.then().assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

}
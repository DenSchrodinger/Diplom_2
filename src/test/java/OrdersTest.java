import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersTest{
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
    @DisplayName("Успешное создание заказа")
    public void statusCodeCheckAfterCorrectOrderCreationTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        String accessToken = methodsApi.getAccessTokenForUser(UserAuthorizationData.from(userRegistrationData));
        String ingredientId = ingredientsApi.getIngredientId(1);
        Response orderResponse = ordersApi.orderCreation(ingredientId, accessToken);
        orderResponse.then().assertThat()
                .body("name", notNullValue())
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Получение заказов авторизированного пользователя")
    public void statusCodeCheckAfterOrderGetForAuthorizedUserTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        String accessToken = methodsApi.getAccessTokenForUser(UserAuthorizationData.from(userRegistrationData));
        Response getOrderResponse = ordersApi.getOrder(accessToken);
        getOrderResponse.then().assertThat()
                .body("total", notNullValue())
                .and()
                .body("orders", notNullValue())
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

}
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestForOrders{
    private MethodsForOrders methodsForOrders;
    private MethodsForUsers methodsForUsers;
    private MethodsForIngredients methodsForIngredients;

    @Before
    public void setUp(){
        methodsForOrders = new MethodsForOrders();
        methodsForUsers = new MethodsForUsers();
        methodsForIngredients = new MethodsForIngredients();
    }

    @Test
    @DisplayName("Успешное создание заказа")
    public void statusCodeCheckAfterCorrectOrderCreationTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        String accessToken = methodsForUsers.getAccessTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        String ingredientId = methodsForIngredients.getIdForIngredient(1);
        Response orderResponse = methodsForOrders.orderCreation(ingredientId, accessToken);
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
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        String accessToken = methodsForUsers.getAccessTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        Response getOrderResponse = methodsForOrders.getOrder(accessToken);
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
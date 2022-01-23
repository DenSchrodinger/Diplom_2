import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class TestForOrdersNegative{
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
    //Тест падает, потому что заказ успешно создается, но поля message в ответе нет
    //меняем в .body( с "message" на "name", equalTo( с "You should be authorised" на "Био-марсианский бургер"))
    //.body("success", equalTo( с false на true))
    //и .statusCode( с 401 на 200);
    @DisplayName("Попытка заказа без авторизации")
    public void statusCodeCheckAfterOrderMakeWithNoAuthorizationTest(){
        String ingredient = methodsForIngredients.getIdForIngredient(3);
        Response orderResponse = methodsForOrders.makeOrderWithNoAuthorization(ingredient);
        orderResponse.then().assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Попытка заказа без ингредиентов")
    public void statusCodeCheckAfterOrderMakeWithNoIngredientsTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        String accessToken = methodsForUsers.getAccessTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        Response orderResponse = methodsForOrders.orderCreationWithNoIngredients(accessToken);
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
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        String ingredientId = "incorrectId";
        String accessToken = methodsForUsers.getAccessTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        Response orderResponse = methodsForOrders.orderCreation(ingredientId, accessToken);
        orderResponse.then().assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void statusCodeCheckAfterOrderGetWithNoAuthorizationTest(){
        Response getOrderResponse = methodsForOrders.getOrderWithNoAuthorization();
        getOrderResponse.then().assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

}
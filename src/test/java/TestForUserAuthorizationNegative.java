import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class TestForUserAuthorizationNegative{
    private MethodsForUsers methodsForUsers;

    @Before
    public void setUp(){
        methodsForUsers = new MethodsForUsers();
    }

    @Test
    @DisplayName("Авторизация пользователя с некорректными данными")
    public void statusCodeCheckAfterWrongUserAuthorizationTest(){
        UserDataForAuthorization userDataForAuthorization = UserDataForAuthorization.getRandomDataForAuthorization();
        Response loginResponse = methodsForUsers.userAuthorization(userDataForAuthorization);
        loginResponse.then().assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

}
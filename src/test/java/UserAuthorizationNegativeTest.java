import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class UserAuthorizationNegativeTest{
    private MethodsApi methodsApi;

    @Before
    public void setUp(){
        methodsApi = new MethodsApi();
    }

    @Test
    @DisplayName("Авторизация пользователя с некорректными данными")
    public void statusCodeCheckAfterWrongUserAuthorizationTest(){
        UserAuthorizationData userAuthorizationData = UserAuthorizationData.getRandomAuthorizationData();
        Response loginResponse = methodsApi.userAuthorization(userAuthorizationData);
        loginResponse.then().assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

}
import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class UserChangeNegativeTest{
    private MethodsApi methodsApi;

    @Before
    public void setUp(){
        methodsApi = new MethodsApi();
    }

    @Test
    @DisplayName("Изменение пользователя без авторизации")
    public void statusCodeCheckAfterUserWithNoAuthorizationChangeTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        UserRegistrationData newUserData = UserRegistrationData.getRandomRegistrationData();
        Response changeResponse = methodsApi.userChangeWithNoAuthorization(newUserData);
        changeResponse.then().assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
        Response loginResponse = methodsApi.userAuthorization(UserAuthorizationData.from(newUserData));
        loginResponse.then().assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }
}

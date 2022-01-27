import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class UserChangeTest{
    private MethodsApi methodsApi;

    @Before
    public void setUp(){
        methodsApi = new MethodsApi();
    }

    @Test
    @DisplayName("Изменение пользователя с авторизацией")
    public void statusCodeCheckAfterCorrectUserChangeTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        String accessToken = methodsApi.getAccessTokenForUser(UserAuthorizationData.from(userRegistrationData));
        UserRegistrationData newUserData = UserRegistrationData.getRandomRegistrationData();
        Response changeResponse = methodsApi.userChange(newUserData, accessToken);
        changeResponse.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
        Response loginResponse = methodsApi.userAuthorization(UserAuthorizationData.from(newUserData));
        loginResponse.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
        String refreshToken = methodsApi.getRefreshTokenForUser(UserAuthorizationData.from(newUserData));
        methodsApi.logout(refreshToken);
    }

}
import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class UserAuthorizationTest{
    private MethodsApi methodsApi;

    @Before
    public void setUp(){
        methodsApi = new MethodsApi();
    }

    @Test
    @DisplayName("Успешная авторизация и выход пользователя")
    public void statusCodeCheckAfterCorrectUserAuthorizationAndLogoutTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        Response loginResponse = methodsApi.userAuthorization(UserAuthorizationData.from(userRegistrationData));
        String accessToken = methodsApi.getAccessTokenForUser(UserAuthorizationData.from(userRegistrationData));
        loginResponse.then().assertThat()
                .body("accessToken", equalTo(accessToken))
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
        String refreshToken = methodsApi.getRefreshTokenForUser(UserAuthorizationData.from(userRegistrationData));
        Response logoutResponse = methodsApi.logout(refreshToken);
        logoutResponse.then().assertThat()
                .body("message", equalTo("Successful logout"))
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

}
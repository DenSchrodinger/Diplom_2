import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class TestForUserAuthorization{
    private MethodsForUsers methodsForUsers;

    @Before
    public void setUp(){
        methodsForUsers = new MethodsForUsers();
    }

    @Test
    @DisplayName("Успешная авторизация и выход пользователя")
    public void statusCodeCheckAfterCorrectUserAuthorizationAndLogoutTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        Response loginResponse = methodsForUsers.userAuthorization(UserDataForAuthorization.from(userDataForRegistration));
        String accessToken = methodsForUsers.getAccessTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        loginResponse.then().assertThat()
                .body("accessToken", equalTo(accessToken))
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
        String refreshToken = methodsForUsers.getRefreshTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        Response logoutResponse = methodsForUsers.logout(refreshToken);
        logoutResponse.then().assertThat()
                .body("message", equalTo("Successful logout"))
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

}
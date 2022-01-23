import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class TestForUserChange{
    private MethodsForUsers methodsForUsers;

    @Before
    public void setUp(){
        methodsForUsers = new MethodsForUsers();
    }

    @Test
    @DisplayName("Изменение пользователя с авторизацией")
    public void statusCodeCheckAfterCorrectUserChangeTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        String accessToken = methodsForUsers.getAccessTokenForUser(UserDataForAuthorization.from(userDataForRegistration));
        UserDataForRegistration newUserData = UserDataForRegistration.getRandomDataForRegistration();
        Response changeResponse = methodsForUsers.userChange(newUserData, accessToken);
        changeResponse.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
        Response loginResponse = methodsForUsers.userAuthorization(UserDataForAuthorization.from(newUserData));
        loginResponse.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
        String refreshToken = methodsForUsers.getRefreshTokenForUser(UserDataForAuthorization.from(newUserData));
        methodsForUsers.logout(refreshToken);
    }

}
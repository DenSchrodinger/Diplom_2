import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class TestForUserChangeNegative{
    private MethodsForUsers methodsForUsers;

    @Before
    public void setUp(){
        methodsForUsers = new MethodsForUsers();
    }

    @Test
    @DisplayName("Изменение пользователя без авторизации")
    public void statusCodeCheckAfterUserWithNoAuthorizationChangeTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        UserDataForRegistration newUserData = UserDataForRegistration.getRandomDataForRegistration();
        Response changeResponse = methodsForUsers.userChangeWithNoAuthorization(newUserData);
        changeResponse.then().assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
        Response loginResponse = methodsForUsers.userAuthorization(UserDataForAuthorization.from(newUserData));
        loginResponse.then().assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }
}

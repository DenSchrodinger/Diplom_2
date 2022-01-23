import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;

public class TestForUserRegistrationNegative {
    private MethodsForUsers methodsForUsers;

    @Before
    public void setUp(){
        methodsForUsers = new MethodsForUsers();
    }

    @Test
    @DisplayName("Регистрация нового пользователя с ранее созданными данными")
    public void statusCodeCheckAfterSecondUserWithSameParametersRegistrationTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUsers.newUserRegistration(userDataForRegistration);
        Response registrationResponse = methodsForUsers.newUserRegistration(userDataForRegistration);
        registrationResponse.then().assertThat()
                .body("message", equalTo("User already exists"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Регистрация нового пользователя без поля почта")
    public void statusCodeCheckForNewUserWithNoLoginRegistrationTest(){
        String bodyWithoutEmail = "{\"password\":\"somepassword\",\"name\":\"somename\"}";
        Response registrationResponse = methodsForUsers.newUserRegistrationWithWrongData(bodyWithoutEmail);
        registrationResponse.then().assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Регистрация нового пользователя без поля пароль")
    public void statusCodeCheckForNewUserWithNoPasswordRegistrationTest(){
        String bodyWithoutEmail = "{\"email\":\"email@test.ru\",\"name\":\"somename\"}";
        Response registrationResponse = methodsForUsers.newUserRegistrationWithWrongData(bodyWithoutEmail);
        registrationResponse.then().assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Регистрация нового пользователя без поля имя")
    public void statusCodeCheckForNewUserWithNoNameRegistrationTest(){
        String bodyWithoutEmail = "{\"email\":\"email@test.ru\",\"password\":\"somepassword\"}";
        Response registrationResponse = methodsForUsers.newUserRegistrationWithWrongData(bodyWithoutEmail);
        registrationResponse.then().assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

}

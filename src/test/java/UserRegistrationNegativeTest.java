import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;

public class UserRegistrationNegativeTest{
    private MethodsApi methodsApi;

    @Before
    public void setUp(){
        methodsApi = new MethodsApi();
    }

    @Test
    @DisplayName("Регистрация нового пользователя с ранее созданными данными")
    public void statusCodeCheckAfterSecondUserWithSameParametersRegistrationTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        methodsApi.newUserRegistration(userRegistrationData);
        Response registrationResponse = methodsApi.newUserRegistration(userRegistrationData);
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
        Response registrationResponse = methodsApi.newUserRegistrationWithWrongData(bodyWithoutEmail);
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
        Response registrationResponse = methodsApi.newUserRegistrationWithWrongData(bodyWithoutEmail);
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
        Response registrationResponse = methodsApi.newUserRegistrationWithWrongData(bodyWithoutEmail);
        registrationResponse.then().assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

}

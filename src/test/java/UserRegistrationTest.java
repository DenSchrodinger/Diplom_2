import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;

public class UserRegistrationTest{
    private MethodsApi methodsApi;

    @Before
    public void setUp(){
        methodsApi = new MethodsApi();
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    public void statusCodeCheckForNewUserRegistrationTest(){
        UserRegistrationData userRegistrationData = UserRegistrationData.getRandomRegistrationData();
        Response registrationResponse = methodsApi.newUserRegistration(userRegistrationData);
        registrationResponse.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

}
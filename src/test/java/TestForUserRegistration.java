import org.junit.Test;
import org.junit.Before;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;

public class TestForUserRegistration{
    private MethodsForUsers methodsForUsers;

    @Before
    public void setUp(){
        methodsForUsers = new MethodsForUsers();
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    public void statusCodeCheckForNewUserRegistrationTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        Response registrationResponse = methodsForUsers.newUserRegistration(userDataForRegistration);
        registrationResponse.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

}
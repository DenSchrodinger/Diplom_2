import java.util.Map;
import java.util.HashMap;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsApi extends RASpecs{
    private static final String API_AUTH = "/api/auth/";

    @Step("Регистрация нового пользователя")
    public Response newUserRegistration(UserRegistrationData registrationData){
        return given()
                .spec(getBaseSpecs())
                .body(registrationData)
                .when()
                .post(API_AUTH + "register");
    }

    @Step("Регистрация нового пользователя с неверными данными")
    public Response newUserRegistrationWithWrongData(String registrationData){
        return given()
                .spec(getBaseSpecs())
                .body(registrationData)
                .when()
                .post(API_AUTH + "register");
    }

    @Step("Получение accessToken пользователя после авторизации")
    public String getAccessTokenForUser(UserAuthorizationData authorizationData){
        return given()
                .spec(getBaseSpecs())
                .body(authorizationData)
                .when()
                .post(API_AUTH + "login")
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Получение refreshToken пользователя после авторизации")
    public String getRefreshTokenForUser(UserAuthorizationData authorizationData){
        return given()
                .spec(getBaseSpecs())
                .body(authorizationData)
                .when()
                .post(API_AUTH + "login")
                .then()
                .extract()
                .path("refreshToken");
    }

    @Step("Вход в систему")
    public Response userAuthorization(UserAuthorizationData authorizationData){
        return given()
                .spec(getBaseSpecs())
                .body(authorizationData)
                .when()
                .post(API_AUTH + "login");
    }

    @Step("Выход из системы")
    public Response logout(String refreshToken){
        Map<String, String> body = new HashMap<>();
        body.put("token", refreshToken);
        return given()
                .spec(getBaseSpecs())
                .body(body)
                .when()
                .post(API_AUTH + "logout");
    }

    @Step("Изменение пользователя")
    public Response userChange(UserRegistrationData registrationData, String accessToken){
        return given()
                .spec(getBaseSpecs())
                .headers("Authorization", accessToken)
                .body(registrationData)
                .when()
                .patch(API_AUTH + "user");
    }

    @Step("Изменение пользователя без авторизации")
    public Response userChangeWithNoAuthorization(UserRegistrationData registrationData){
        return given()
                .spec(getBaseSpecs())
                .body(registrationData)
                .when()
                .patch(API_AUTH + "user");
    }

}
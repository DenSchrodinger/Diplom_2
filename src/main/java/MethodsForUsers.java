import java.util.Map;
import java.util.HashMap;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsForUsers extends RASpecs {
    private static final String API_AUTH = "/api/auth/";

    @Step("Регистрация нового пользователя")
    public Response newUserRegistration(UserDataForRegistration dataForRegistration){
        return given()
                .spec(getBaseSpecs())
                .body(dataForRegistration)
                .when()
                .post(API_AUTH + "register");
    }

    @Step("Регистрация нового пользователя с неверными данными")
    public Response newUserRegistrationWithWrongData(String dataForRegistration){
        return given()
                .spec(getBaseSpecs())
                .body(dataForRegistration)
                .when()
                .post(API_AUTH + "register");
    }

    @Step("Получение accessToken пользователя после авторизации")
    public String getAccessTokenForUser(UserDataForAuthorization dataForAuthorization){
        return given()
                .spec(getBaseSpecs())
                .body(dataForAuthorization)
                .when()
                .post(API_AUTH + "login")
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Получение refreshToken пользователя после авторизации")
    public String getRefreshTokenForUser(UserDataForAuthorization dataForAuthorization){
        return given()
                .spec(getBaseSpecs())
                .body(dataForAuthorization)
                .when()
                .post(API_AUTH + "login")
                .then()
                .extract()
                .path("refreshToken");
    }

    @Step("Вход в систему")
    public Response userAuthorization(UserDataForAuthorization dataForAuthorization){
        return given()
                .spec(getBaseSpecs())
                .body(dataForAuthorization)
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
    public Response userChange(UserDataForRegistration dataForRegistration, String accessToken){
        return given()
                .spec(getBaseSpecs())
                .headers("Authorization", accessToken)
                .body(dataForRegistration)
                .when()
                .patch(API_AUTH + "user");
    }

    @Step("Изменение пользователя без авторизации")
    public Response userChangeWithNoAuthorization(UserDataForRegistration dataForRegistration){
        return given()
                .spec(getBaseSpecs())
                .body(dataForRegistration)
                .when()
                .patch(API_AUTH + "user");
    }

}
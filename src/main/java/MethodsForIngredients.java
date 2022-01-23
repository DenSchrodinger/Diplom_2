import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class MethodsForIngredients extends RASpecs {
    private static final String API_INGREDIENTS = "/api/ingredients";

    @Step("Получение id для ингредиента")
    public String getIdForIngredient(int massiveId){
        return given()
                .spec(getBaseSpecs())
                .get(API_INGREDIENTS)
                .then()
                .extract()
                .path("data["+ massiveId + "]._id");
    }
}
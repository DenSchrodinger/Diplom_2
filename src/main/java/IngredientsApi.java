import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class IngredientsApi extends RASpecs{
    private static final String API_INGREDIENTS = "/api/ingredients";

    @Step("Получение id для ингредиента")
    public String getIngredientId(int massiveId){
        return given()
                .spec(getBaseSpecs())
                .get(API_INGREDIENTS)
                .then()
                .extract()
                .path("data["+ massiveId + "]._id");
    }
}
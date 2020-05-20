package si.uni_lj.fri.pbd.miniapp3.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeDetailsIM;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientsDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipeSummaryDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIdDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIngredientDTO;

public interface RestAPI {

    @GET("list.php?i=list")
    Call<IngredientsDTO> getAllIngredients();
    @GET("filter.php")
    Call<RecipesByIngredientDTO> getfood(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<RecipesByIdDTO> getMealDetails(@Query("i") int mealId);


}
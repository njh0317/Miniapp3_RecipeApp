package si.uni_lj.fri.pbd.miniapp3.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.models.RecipeSummaryIM;

public class RecipesByIngredientDTO {

    @SerializedName("meals")
    @Expose
    private List<RecipeSummaryIM> meals = null;

    public List<RecipeSummaryIM> getRecipes() {
        return meals;
    }

    public void setRecipes(List<RecipeSummaryIM> meals) {
        this.meals = meals;
    }
}

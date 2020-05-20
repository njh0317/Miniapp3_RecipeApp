package si.uni_lj.fri.pbd.miniapp3.models.dto;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.models.RecipeDetailsIM;

public class RecipesByIdDTO {
    private List<RecipeDetailsIM> meals=null;

    public void setMeals(List<RecipeDetailsIM> meals) {
        this.meals = meals;
    }

    public List<RecipeDetailsIM> getMeals() {
        return meals;
    }
}

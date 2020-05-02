package si.uni_lj.fri.pbd.miniapp3.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM RecipeDetails WHERE idMeal = :idMeal")
    RecipeDetails getRecepiById(String idMeal);

    // TODO: Add the missing methods
}

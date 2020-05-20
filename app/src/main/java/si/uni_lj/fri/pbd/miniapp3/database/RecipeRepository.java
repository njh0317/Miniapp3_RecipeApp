package si.uni_lj.fri.pbd.miniapp3.database;
import android.app.Application;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import si.uni_lj.fri.pbd.miniapp3.database.dao.RecipeDao;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;

public class RecipeRepository {

    private MutableLiveData<RecipeDetails> searchResults;
    private LiveData<List<RecipeDetails>> allRecipes;
    private RecipeDao recipeDao;

    public RecipeRepository(Application application){
        Database db;
        db=Database.getDatabase(application);
        recipeDao=db.recipeDao();
        allRecipes = recipeDao.getAllFood();
    }


    public void insertRecipe(final RecipeDetails newrecipe) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.insertRecipe(newrecipe);
            }
        });
    }


    public void deleteRecipe(final String id) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.deleteRecipe(id);
            }
        });
    }

    public void findRecipe(final String name) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                searchResults.postValue(recipeDao.getRecepiById(name));

            }
        });
    }

    public LiveData<List<RecipeDetails>> getAllRecipes() {
        return allRecipes;
    }

    public MutableLiveData<RecipeDetails> getSearchResults() {
        if (searchResults == null) {
            searchResults = new MutableLiveData<RecipeDetails>();
        }
        return searchResults;
    }

}

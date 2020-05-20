package si.uni_lj.fri.pbd.miniapp3.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;

public class MainViewModel extends AndroidViewModel {
    LiveData<List<RecipeDetails>> allProducts;
    MutableLiveData<RecipeDetails> searchResults;

    private RecipeRepository repository;

    public MainViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
        allProducts = repository.getAllRecipes();
        searchResults = repository.getSearchResults();

    }

    public MutableLiveData<RecipeDetails> getSearchResults(){
        return searchResults;
    }
    public LiveData<List<RecipeDetails>> getAllRecipes(){
        return allProducts;
    }

    public void insertProduct(RecipeDetails recipe){
        repository.insertRecipe(recipe);
    }
    public void findProduct(String name){
        repository.findRecipe(name);
    }
    public void deleteProduct(String name){
        repository.deleteRecipe(name);
    }

}

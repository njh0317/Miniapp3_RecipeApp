package si.uni_lj.fri.pbd.miniapp3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.database.Database;
import si.uni_lj.fri.pbd.miniapp3.database.dao.RecipeDao;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.models.Mapper;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeDetailsIM;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIdDTO;
import si.uni_lj.fri.pbd.miniapp3.rest.RestAPI;
import si.uni_lj.fri.pbd.miniapp3.rest.ServiceGenerator;

public class DetailsActivity extends AppCompatActivity {
    int beforeActivity;
    String foodnum;
    TextView strMeal;
    TextView strCountry;
    TextView ingredients;
    TextView measurements;
    TextView instructions;
    ImageView image;
    Database db;
    ArrayList<RecipeDetailsIM> list;

    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        but = (Button)findViewById(R.id.button);

        foodnum = intent.getStringExtra("id");
        beforeActivity = intent.getIntExtra("nowActivity",0);
        if(beforeActivity==2){
            but.setText(R.string.delete);
        }
        strMeal=(TextView)findViewById(R.id.textview_name);
        strCountry=(TextView)findViewById(R.id.textview_country);
        instructions=(TextView)findViewById(R.id.textview_instruction2);
        ingredients=(TextView)findViewById(R.id.textview_ingredients2);
        measurements=(TextView)findViewById(R.id.textview_measurements2);
        image = (ImageView)findViewById(R.id.imageview);
        db = Database.getDatabase(this);

        if(beforeActivity == 1) {
            findDetails();
        }else if(beforeActivity == 2){
            try {
                observerSetup();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!foodnum.equals("")) {
                    if(but.getText().toString().equals(getString(R.string.delete)))
                    {
                        new deleteAsyncTask(db.recipeDao()).execute(list.get(0).getIdMeal());
                        //mViewModel.deleteProduct(list.get(0).getIdMeal());
                        but.setText(R.string.add);
                    }
                    else if(but.getText().toString().equals(getString(R.string.add))) {
                        RecipeDetails recipe = Mapper.mapRecipeDetailsIMToRecipeDetails(true, list.get(0));
                        new InsertAsyncTask(db.recipeDao()).execute(recipe);
                        //mViewModel.insertProduct(recipe);
                        but.setText(R.string.delete);
                    }
                }
            }
        });

    }
    private void observerSetup() throws ExecutionException, InterruptedException {
        RecipeDetails products = new findAsyncTask(db.recipeDao()).execute(foodnum).get();

        if (products!=null) {

            RecipeDetailsIM recipe = Mapper.mapRecipeDetailsToRecipeDetailsIm(true,products);
            list = new ArrayList<RecipeDetailsIM>();
            list.add(recipe);
            strMeal.setText(recipe.getStrMeal());
            strCountry.setText(recipe.getStrArea());
            Glide.with(DetailsActivity.this).load(recipe.getStrMealThumb()).into(image);
            instructions.setText(recipe.getStrInstructions());
            ingredients.setText(recipe.ingredientString());
            measurements.setText(recipe.measurementString());
        }
        if(beforeActivity == 1)
        {
            if(products!=null){
                but.setText(R.string.delete);
            }
        }

    }

    private void findDetails(){
        RestAPI api;
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        api = serviceGenerator.createService(RestAPI.class);
        Call<RecipesByIdDTO> call = api.getMealDetails(Integer.parseInt(foodnum));
        call.enqueue(new Callback<RecipesByIdDTO>()
        {
            @Override
            public void onResponse(Call<RecipesByIdDTO> call, Response<RecipesByIdDTO> response) {
                RecipesByIdDTO food = response.body();
                list = (ArrayList<RecipeDetailsIM>) food.getMeals();
                if(list!=null) {
                    try {
                        observerSetup();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    strMeal.setText(list.get(0).getStrMeal());
                    strCountry.setText(list.get(0).getStrArea());
                    Glide.with(DetailsActivity.this).load(list.get(0).getStrMealThumb()).into(image);
                    instructions.setText(list.get(0).getStrInstructions());
                    ingredients.setText(list.get(0).ingredientString());
                    measurements.setText(list.get(0).measurementString());
                }
                else{

                }
            }
            @Override
            public void onFailure(Call<RecipesByIdDTO> call, Throwable t) {

            }});

    }
    public static class InsertAsyncTask extends AsyncTask<RecipeDetails, Void, Void> {
        private RecipeDao recipedao;

        public  InsertAsyncTask(RecipeDao recipedao){
            this.recipedao = recipedao;
        }

        @Override
        protected Void doInBackground(RecipeDetails... recipeDetails) {
            recipedao.insertRecipe(recipeDetails[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<String, Void, Void> {
        private RecipeDao recipedao;

        public deleteAsyncTask(RecipeDao recipedao){
            this.recipedao = recipedao;
        }

        @Override
        protected Void doInBackground(String... id) {
            recipedao.deleteRecipe(id[0]);
            return null;
        }
    }

    public static class findAsyncTask extends AsyncTask<String, Void, RecipeDetails> {
        private RecipeDao recipedao;

        public findAsyncTask(RecipeDao recipedao){
            this.recipedao = recipedao;
        }

        @Override
        protected RecipeDetails doInBackground(String... id) {
            return recipedao.getRecepiById(id[0]);

        }
    }

}

package si.uni_lj.fri.pbd.miniapp3.ui.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.RecyclerViewAdapter;
import si.uni_lj.fri.pbd.miniapp3.adapter.SpinnerAdapter;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeSummaryIM;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientsDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIngredientDTO;
import si.uni_lj.fri.pbd.miniapp3.rest.RestAPI;
import si.uni_lj.fri.pbd.miniapp3.rest.ServiceGenerator;
import si.uni_lj.fri.pbd.miniapp3.ui.Network;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment {
    private MaterialProgressBar mprogressbar;
    private String[] item;
    private Spinner spinner;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int status;
    private TextView view1;
    private long lastupdate = 0;
    private long now;
    private String nowingrId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callIngredient();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view1 = getActivity().findViewById(R.id.textview);
        view1.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipe);
        mprogressbar = getActivity().findViewById(R.id.progress);
        mprogressbar.setIndeterminateDrawable(new IndeterminateHorizontalProgressDrawable(getActivity()));
        mprogressbar.setVisibility(View.GONE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        status = Network.getConnectivityStatus(getActivity());
                        System.out.println("run : "+status);
                        if(status == 3) connectionAlert();
                        else {
                            now = System.currentTimeMillis()/1000;
                            if(now - lastupdate<5) {
                                System.out.println("if : " + status);
                                Toast.makeText(getActivity(), "swipe comes within 5 seconds since the last data download",
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                //callIngredient();
                                System.out.println("else : " + nowingrId);
                                if(nowingrId ==null)
                                {
                                    callIngredient();
                                }
                                else {

                                    callfoodbyingredient(nowingrId);
                                }
                            }
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);

            }
        });
        recyclerSetup();
    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        status = Network.getConnectivityStatus(getActivity());
        if(status == 3) connectionAlert();
        View view;
        view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }
    private void connectionAlert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Connection Issue");
        alert.setMessage("It could not connect to the server\nTurn on WIFI or Data to connect to server:)")
                .setNegativeButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
    private void callIngredient() {
        RestAPI api;
        api = ServiceGenerator.createService(RestAPI.class);
        Call<IngredientsDTO> call = api.getAllIngredients();

        call.enqueue(new Callback<IngredientsDTO>() {
            @Override
            public void onResponse(@NotNull Call<IngredientsDTO> call, @NotNull Response<IngredientsDTO> response) {
                IngredientsDTO ingredients = response.body();

                ArrayList<IngredientDTO> list = (ArrayList<IngredientDTO>) ingredients.getIngredients();
                if(list!=null&&list.size()>0)
                {
                    item = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        item[i]=list.get(i).getStrIngredient();
                    }
                    spinner = (Spinner)getActivity().findViewById(R.id.spinner);
                    spinner.setPrompt("choose ingredient");
                    SpinnerAdapter adapter = new SpinnerAdapter(item, getActivity());
                    spinner.setAdapter(adapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getActivity(),item[position],Toast.LENGTH_SHORT).show();
                            nowingrId = item[position];
                            callfoodbyingredient(nowingrId);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) { }
                    });

                }
                else{
                    item = new String[1];
                    item[0] = "no Ingredient";
                    SpinnerAdapter adapter = new SpinnerAdapter(item, getActivity());
                    spinner.setAdapter(adapter);

                }
            }
            @Override
            public void onFailure(@NotNull Call<IngredientsDTO> call, @NotNull Throwable t) {
            }
        });
    }

    private void callfoodbyingredient(String ingred){
        mprogressbar.setVisibility(View.VISIBLE);
        RestAPI api;
        api = ServiceGenerator.createService(RestAPI.class);
        Call<RecipesByIngredientDTO> call = api.getfood(ingred);
        call.enqueue(new Callback<RecipesByIngredientDTO>()
        {
            @Override
            public void onResponse(Call<RecipesByIngredientDTO> call, Response<RecipesByIngredientDTO> response) {
                RecipesByIngredientDTO food = response.body();
                ArrayList<RecipeSummaryIM> list = (ArrayList<RecipeSummaryIM>) food.getRecipes();
                if(list!=null&&list.size()>0)
                {
                    view1.setVisibility(View.GONE);
                    adapter.setRecipelist(list);
                }
                else if(list==null)
                {
                    adapter.setRecipelist(list);
                    Toast.makeText(getActivity(),"No recipe",
                            Toast.LENGTH_LONG).show();
                    view1.setVisibility(View.VISIBLE);

                }
                mprogressbar.setVisibility(View.GONE);
                lastupdate = System.currentTimeMillis()/1000;

            }
            @Override
            public void onFailure(@NotNull Call<RecipesByIngredientDTO> call, @NotNull Throwable t) {

            }});

    }

    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new RecyclerViewAdapter(1);
        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));//getActivity()?
        recyclerView.setAdapter(adapter);
    }

}

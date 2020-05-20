package si.uni_lj.fri.pbd.miniapp3.ui.favorites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.RecyclerViewAdapter;
import si.uni_lj.fri.pbd.miniapp3.database.MainViewModel;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.models.Mapper;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeSummaryIM;

public class FavoritesFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private MainViewModel mViewModel;
    private RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipe2);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);

            }
        });
        observerSetup();
        recyclerSetup();
    }

    private void observerSetup() {
        mViewModel.getAllRecipes().observe(getViewLifecycleOwner(),
                new Observer<List<RecipeDetails>>(){
                    @Override
                    public void onChanged(@Nullable final List<RecipeDetails> products) {
                        List<RecipeSummaryIM> recipesummarylist = new ArrayList<>();
                        for(int i=0;i<products.size();i++) {
                            recipesummarylist.add(Mapper.mapRecipeDetailsToRecipeSummaryIm(products.get(i)));
                        }
                        adapter.setRecipelist(recipesummarylist);
                    }
                });
    }
    private void recyclerSetup() {

        RecyclerView recyclerView;
        adapter = new RecyclerViewAdapter(2);
        recyclerView = getView().findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));//getActivity()?
        recyclerView.setAdapter(adapter);
    }

}

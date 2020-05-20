package si.uni_lj.fri.pbd.miniapp3.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int NUM_OF_TABS = 2;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private SectionsPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureTabLayout();

    }
    private void configureTabLayout(){
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        viewPager = (ViewPager2)findViewById(R.id.viewpager);
        pagerAdapter = new SectionsPagerAdapter(this,NUM_OF_TABS);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager,
                new TabLayoutMediator.TabConfigurationStrategy(){
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch(position){
                            case 0:
                                tab.setText("SEARCH RECIPE BY INGREDIENT");
                                return;
                            case 1:
                                tab.setText("FAVORITE RECIPES");
                                return;
                        }
                    }
                }).attach();
    }

}

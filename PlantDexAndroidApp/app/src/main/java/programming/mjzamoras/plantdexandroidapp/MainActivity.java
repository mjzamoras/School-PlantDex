package programming.mjzamoras.plantdexandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private FrameLayout fl;
    private NavigationView navView;
    private ActionBarDrawerToggle toggle;

    private ClassifyFragment classifyFragment;
    private MapFragment mapFragment;
    private SearchFragment searchFragment;
    private String reroute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout) findViewById(R.id.activity_menu);
        fl = (FrameLayout) findViewById(R.id.layout_frame);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);



        dl.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = (NavigationView) findViewById(R.id.navigationDrawer);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.nav_item_classify:
                        SetFragment(classifyFragment);
                        dl.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.nav_item_search:
                        SetFragment(searchFragment);
                        dl.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.nav_item_contribute:
                        Toast.makeText(MainActivity.this, "Contribute", Toast.LENGTH_LONG).show();
                        dl.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.nav_item_map:
                        SetFragment(mapFragment);
                        dl.closeDrawer(GravityCompat.START);
                        return true;

                    default:

                        dl.closeDrawer(GravityCompat.START);
                        return true;
                }
            }
        });


        classifyFragment = new ClassifyFragment();
        mapFragment = new MapFragment();
        searchFragment = new SearchFragment();

        try{
            reroute = getIntent().getStringExtra("reroute").toString();

            switch (reroute){
                case "map":
                    SetFragment(mapFragment);
                    break;
                case "classify":
                    SetFragment(classifyFragment);
                    break;
                case "search":
                    SetFragment(searchFragment);
                    break;
            }
        }catch (Exception ex){

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_frame, fragment);
        transaction.commit();
    }
}

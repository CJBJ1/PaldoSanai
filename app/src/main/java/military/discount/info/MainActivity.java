package military.discount.info;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import military.discount.info.ui.favorite.FavoriteFragment;
import military.discount.info.ui.home.HomeFragment;
import military.discount.info.ui.send.SendFragment;
import military.discount.info.ui.share.ShareFragment;
import military.discount.info.ui.info.InfoFragment;
import military.discount.info.ui.shopReg.ShopRegFragment;

import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private LatLng centerPoint;
    private int zoomLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorite, R.id.nav_info,
                R.id.nav_shopReg, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        String title = "My Location";
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //fragment 쌓임 방지 (임시)
        navController.popBackStack(R.id.nav_home,false);

        if (id == R.id.nav_home) {
            navController.popBackStack();
            navController.navigate(R.id.nav_home);
        } else if (id == R.id.nav_favorite) {
            navController.navigate(R.id.nav_favorite);
        } else if (id == R.id.nav_info) {
            navController.navigate(R.id.nav_info);
        } else if (id == R.id.nav_shopReg) {
            navController.navigate(R.id.nav_shopReg);
        } else if (id == R.id.nav_share) {
            navController.navigate(R.id.nav_share);
        } else if (id == R.id.nav_send) {
            navController.navigate(R.id.nav_send);
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    /*public void setUpFragmentArray(){
        Fragment homeFragment = new HomeFragment();
        Fragment galleryFragment = new GalleryFragment();
        Fragment slideshowFragment = new SlideshowFragment();
        Fragment toolsFragment = new ToolsFragment();
        Fragment shareFragment = new ShareFragment();
        Fragment sendFragment = new SendFragment();
        fragmentArrayList.add(homeFragment);
        fragmentArrayList.add(galleryFragment);
        fragmentArrayList.add(slideshowFragment);
        fragmentArrayList.add(toolsFragment);
        fragmentArrayList.add(shareFragment);
        fragmentArrayList.add(sendFragment);
    }*/
}

package military.discount.info;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import military.discount.info.login.IdTokenActivity;
import military.discount.info.login.ServerAuthCodeActivity;
import military.discount.info.login.SignInActivity;
import military.discount.info.ui.favorite.FavoriteFragment;
import military.discount.info.ui.home.HomeFragment;
import military.discount.info.ui.send.SendFragment;
import military.discount.info.ui.share.ShareFragment;
import military.discount.info.ui.info.InfoFragment;
import military.discount.info.ui.shopReg.ShopRegFragment;

import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private LatLng centerPoint;
    private ShopList shopList;
    private JSONArray jsArr;
    private int zoomLevel;
    private int REQUEST_FAVORITE = 100;
    private int RESULT_FAVORITE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shopList = (ShopList)getApplicationContext();


        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
        navController.popBackStack(R.id.nav_home, false);

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
            startActivity(new Intent(this, ServerAuthCodeActivity.class));
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getFragments().get(0);
        FavoriteFragment favoriteFragment = (FavoriteFragment)navHostFragment.getChildFragmentManager().getFragments().get(0);

        Log.d("request",requestCode + "");
        Log.d("result",resultCode + "");
        if (requestCode == REQUEST_FAVORITE) {
            if (resultCode == RESULT_FAVORITE ) {
                Log.d("정확한 전달",data.getExtras().getDouble("lat")+ "");
                Toast.makeText(getApplicationContext(),"전달!",Toast.LENGTH_LONG).show();

                LatLng latLng = new LatLng(data.getExtras().getDouble("lat"),data.getExtras().getDouble("lng"));
                favoriteFragment.getFavoriteViewModel().getAdapter().getPositions().add(latLng);
                favoriteFragment.getFavoriteViewModel().getAdapter().getmData().add(data.getExtras().getString("name"));
                favoriteFragment.getFavoriteViewModel().getAdapter().notifyDataSetChanged();
            } else {   // RESULT_CANCEL.
                Log.d("뭐지","뭐지");
                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_LONG).show();
            }
//        } else if (requestCode == REQUEST_ANOTHER) {
//            ...
        }

    }

    public void goHome(){
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.nav_home, fragment );
        fragmentTransaction.commit();
    }

}

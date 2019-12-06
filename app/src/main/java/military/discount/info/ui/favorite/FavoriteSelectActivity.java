package military.discount.info.ui.favorite;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import military.discount.info.MainActivity;
import military.discount.info.R;
import military.discount.info.ShopList;

public class FavoriteSelectActivity extends FragmentActivity implements OnCameraMoveStartedListener,
        OnCameraMoveListener,
        OnCameraMoveCanceledListener,
        OnCameraIdleListener,
        OnMapReadyCallback{

    GoogleMap mMap;
    Marker activeMarker;
    private Button addButton;
    private Button backButton;
    private int RESULT_FAVORITE = 101;
    private int RESULT_FAVORITE_FAIL = 102;
    String name = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_select);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_favorite);
        mapFragment.getMapAsync(this);
        addButton = findViewById(R.id.button_favorite_add);
        backButton = findViewById(R.id.button_favorite_back);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activeMarker!=null) {
                    setName();
                }

                else{
                    Intent intent = new Intent();
                    setResult(RESULT_FAVORITE_FAIL ,intent);
                    finish();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_FAVORITE_FAIL ,intent);
                finish();
            }
        });
    }

    @Override //구글맵을 띄울준비가 됬으면 자동호출된다.
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.user_marker);
        Bitmap b=bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, 200, 200, false);

        LatLng center = new LatLng(37.504198, 126.956875);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(activeMarker!=null) {
                    activeMarker.remove();
                }
                MarkerOptions marker = new MarkerOptions();
                marker.position(latLng);
                marker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                activeMarker = mMap.addMarker(marker);
            }
        });
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == OnCameraMoveStartedListener.REASON_GESTURE) {
        } else if (reason == OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
        } else if (reason == OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
        }
    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    public void setName(){
        AlertDialog.Builder ad = new AlertDialog.Builder(FavoriteSelectActivity.this);

        ad.setTitle("해당 위치의 명칭 입력");       // 제목 설정
        ad.setMessage("Message");   // 내용 설정

        final EditText et = new EditText(FavoriteSelectActivity.this);
        ad.setView(et);
        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = et.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("lat", activeMarker.getPosition().latitude);
                intent.putExtra("lng", activeMarker.getPosition().longitude);
                intent.putExtra("name",name);
                setResult(RESULT_FAVORITE, intent);
                dialog.dismiss();

                finish();
            }
        });

        ad.setNeutralButton("X", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = "Location";
                Intent intent = new Intent();
                intent.putExtra("lat", activeMarker.getPosition().latitude);
                intent.putExtra("lng", activeMarker.getPosition().longitude);
                intent.putExtra("name",name);
                setResult(RESULT_FAVORITE, intent);
                dialog.dismiss();

                finish();
                dialog.dismiss();
            }
        });

        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = "Location";
                dialog.dismiss();
            }
        });

        ad.show();

    }

}

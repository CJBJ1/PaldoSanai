package military.discount.info.ui.home;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.EditText;
import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import military.discount.info.GeoLocation;
import military.discount.info.R;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Activity parentActivity;
    private ArrayList<MarkerOptions> markerList = new ArrayList<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void setParentActivity(Activity activity){
        parentActivity = activity;
    }

    //상단바 검색시 주소 -> 좌표로 변환
    public void setCenterByText(String address , GoogleMap mMap, Context context){
        Geocoder geocoder = new Geocoder(context);

        ArrayList<GeoLocation> resultList = new ArrayList<>();

        try {
            List<Address> list = geocoder.getFromLocationName(address, 10);

            for (Address addr : list) {
                resultList.add(new GeoLocation(addr.getLatitude(), addr.getLongitude()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!resultList.isEmpty()) {
            LatLng center = new LatLng(resultList.get(0).getLatitude(), resultList.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        }
    }

    public void showShopInfo(FragmentManager fm,LatLng centerPoint){
        fm.popBackStack();

        Fragment inf = new InfoFragment(centerPoint);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom,R.anim.enter_to_bottom,R.anim.enter_from_bottom,R.anim.enter_to_bottom);
        transaction.add(R.id.shopInfo, inf);
        transaction.commit();
        transaction.addToBackStack(null);
    }

    public void setMap(final GoogleMap mMap, final FragmentManager fm){

        GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showShopInfo(fm, marker.getPosition());
                return false;
            }
        };

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });


        //초기 중심점 중앙대로 설정
        LatLng center = new LatLng(37.504198, 126.956875);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(center)
                .title("마커");
        mMap.addMarker(markerOptions);
        mMap.addMarker(new MarkerOptions().position(new LatLng(37.404235,126.545156)));
        mMap.setOnMarkerClickListener(markerClickListener);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
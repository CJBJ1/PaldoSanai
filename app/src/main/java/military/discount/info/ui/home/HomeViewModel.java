package military.discount.info.ui.home;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import military.discount.info.GeoLocation;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
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
}
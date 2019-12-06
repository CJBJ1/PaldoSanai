package military.discount.info.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import military.discount.info.GeoLocation;
import military.discount.info.MainActivity;
import military.discount.info.R;
import military.discount.info.RequestHttpURLConnection;
import military.discount.info.Shop;
import military.discount.info.ShopList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Activity parentActivity;
    private ArrayList<MarkerOptions> markerList = new ArrayList<>();
    private ShopList shopList;
    private Context context;
    private JSONArray jsArr;
    private GoogleMap mMap;
    private FragmentManager fm;

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

    public void showShopInfo(FragmentManager fm,int index){
        fm.popBackStack();

        Fragment inf = new InfoFragment(shopList.getShopArrayList().get(index));
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom,R.anim.enter_to_bottom,R.anim.enter_from_bottom,R.anim.enter_to_bottom);
        transaction.add(R.id.shopInfo, inf);
        transaction.commit();
        transaction.addToBackStack(null);


    }

    public void setNetwork(final GoogleMap mMap, final FragmentManager fm,Context context){
        shopList = (ShopList)context;

        this.mMap=mMap;
        this.fm = fm;
        this.context = context;

        String url = "http://54.180.83.196:8888/places";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
    }

    public void setMap(final GoogleMap mMap, final FragmentManager fm,Context context){

        GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showShopInfo(fm, Integer.parseInt(marker.getTag().toString()));
                return false;
            }
        };

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        for (int index =0;index<shopList.getShopArrayList().size();index++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.parseDouble(shopList.getShopArrayList().get(index).getLat()), Double.parseDouble(shopList.getShopArrayList().get(index).getLng())));
            mMap.addMarker(markerOptions).setTag(index);
        }


        if(shopList.getCenterPoint()==null) {
            LatLng center = new LatLng(37.504198, 126.956875);
            mMap.setOnMarkerClickListener(markerClickListener);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
        else{
            LatLng center =shopList.getCenterPoint();
            mMap.setOnMarkerClickListener(markerClickListener);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            shopList.setCenterPoint(null);
        }

    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "basic"; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                int index=0;

                jsArr = new JSONArray(s);
                while(index != jsArr.length()){
                    parseShop(jsArr,index);
                    index++;
                }

                setMap(mMap,fm,context);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void parseShop(JSONArray jsonArray,int index){
            Shop shop = new Shop();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                shop.setId(jsonObject.getString("id"));
                shop.setName(jsonObject.getString("name"));
                shop.setAddress(jsonObject.getString("address"));
                shop.setDescription(jsonObject.getString("description"));
                shop.setPhone(jsonObject.getString("phone"));
                shop.setPage_url(jsonObject.getString("page_url"));
                shop.setStart_date(jsonObject.getString("start_date"));
                shop.setEnd_date(jsonObject.getString("end_date"));
                shop.setInformation(jsonObject.getString("information"));
                shop.setRegistration_num(jsonObject.getString("registration_num"));
                shop.setActive(jsonObject.getString("active"));
                shop.setLat(jsonObject.getString("latitude"));
                shop.setLng(jsonObject.getString("longitude"));

                shopList.getShopArrayList().add(shop);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
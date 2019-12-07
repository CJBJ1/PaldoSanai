package military.discount.info.ui.favorite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import military.discount.info.RecyclerDecoration;
import military.discount.info.RequestHttpURLConnection;
import military.discount.info.Shop;
import military.discount.info.ShopList;
import military.discount.info.SimpleTextAdapter;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<LatLng> positions = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();

    private RecyclerView recyclerView;
    private Context context;
    private Activity activity;
    private SimpleTextAdapter adapter;
    private ShopList shopList;
    private JSONArray jsArr;

    public FavoriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("자주 가는 장소");
    }

    public void setRecyclerView(RecyclerView recyclerView, Context context, Activity activity){
        shopList = (ShopList)activity.getApplicationContext();

        this.recyclerView = recyclerView;
        this.context = context;
        this.activity = activity;

        String url = "http://8dosanai.com:8888/favorites/";
        NetworkTaskFavorite networkTaskFavorite = new NetworkTaskFavorite(url,null);
        networkTaskFavorite.execute();

    }

    public LiveData<String> getText() {
        return mText;
    }
    public SimpleTextAdapter getAdapter() {return adapter;}

    public class NetworkTaskFavorite extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTaskFavorite(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            requestHttpURLConnection.setAccessToken(shopList.getAccessToken());
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                Log.d("favorite 확인!", s);
                try {
                    int index = 0;
                    jsArr = new JSONArray(s);
                    while (index != jsArr.length()) {

                        JSONObject jsonObject = jsArr.getJSONObject(index);
                        mData.add(jsonObject.getString("name"));
                        positions.add(new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude")));
                        id.add(jsonObject.getString("id"));

                        index++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(context)) ;
            adapter = new SimpleTextAdapter(mData,positions,id) ;
            adapter.setContext(context);
            adapter.setActivity(activity);

            recyclerView.addItemDecoration(new RecyclerDecoration(20));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter) ;

        }

    }
}
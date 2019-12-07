package military.discount.info.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import military.discount.info.MainActivity;
import military.discount.info.ModifyActivity;
import military.discount.info.R;
import military.discount.info.RecyclerDecoration;
import military.discount.info.RequestHttpURLConnection;
import military.discount.info.Shop;
import military.discount.info.ShopList;
import military.discount.info.SimpleTextAdapter;
import military.discount.info.login.IdTokenActivity;
import military.discount.info.ui.favorite.FavoriteSelectActivity;

public class InfoFragment extends Fragment  {

    private Button likeButton;
    private LatLng markerPosition;
    private Shop shop;
    private TextView name;
    private TextView address;
    private TextView information;
    private TextView phone;
    private TextView url;
    private TextView likes;
    private String id;
    private Button modifyButton;
    private ShopList shopList;

    private boolean like;

    public InfoFragment(Shop shop){
        this.shop = shop;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        shopList = (ShopList)getActivity().getApplicationContext();
        View root = inflater.inflate(R.layout.shop_info_view, container, false);
        like = false;

        name = (TextView)root.findViewById(R.id.textView5);
        address = (TextView)root.findViewById(R.id.textView6);
        information =(TextView)root.findViewById(R.id.textView8);
        phone = (TextView)root.findViewById(R.id.textView9);
        url = (TextView)root.findViewById(R.id.textView10);
        likes = (TextView)root.findViewById(R.id.textView7);
        modifyButton = (Button)root.findViewById(R.id.button2);
        likeButton = (Button)root.findViewById(R.id.button);

        name.setText(shop.getName());
        address.setText(shop.getAddress());
        information.setText( "-" + shop.getDescription());
        phone.setText("-" + shop.getPhone());
        url.setText("-" + shop.getPage_url());
        likes.setText(shop.getLikes());

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ModifyActivity.class);
                getActivity().startActivityForResult(intent,200);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopList.getAccessToken() != null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("place", shop.getId());
                    String url = "http://8dosanai.com:8888/likes/do/";
                    NetworkTaskLikes networkTask = new NetworkTaskLikes(url, contentValues);
                    networkTask.execute();
                }
            }
        });

        return root;
    }

    public class NetworkTaskLikes extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTaskLikes(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            requestHttpURLConnection.setAccessToken(shopList.getAccessToken());
            result = requestHttpURLConnection.requestPost(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("좋아요응답",s);
            if(s.equals("[1,{\"discountplace.Like\":1}]")){
                int likecount = Integer.parseInt(shop.getLikes());
                likecount--;
                shop.setLikes(likecount + "");
                likes.setText(shop.getLikes());
            }else{
                int likecount = Integer.parseInt(shop.getLikes());
                likecount++;
                shop.setLikes(likecount + "");
                likes.setText(shop.getLikes());
            }


        }

    }

}
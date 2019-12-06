package military.discount.info.ui.favorite;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import military.discount.info.DividerItemDecoration;
import military.discount.info.MainActivity;
import military.discount.info.R;
import military.discount.info.RecyclerDecoration;
import military.discount.info.RequestHttpURLConnection;
import military.discount.info.ShopList;
import military.discount.info.SimpleTextAdapter;
import military.discount.info.ui.home.HomeFragment;
import military.discount.info.ui.home.HomeViewModel;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private Button deleteButton;
    private Button addButton;
    private ShopList shopList;
    private int REQUEST_FAVORITE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopList = (ShopList)getActivity().getApplicationContext();
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_item) ;
        favoriteViewModel.setRecyclerView(recyclerView,getContext(),getActivity());

        addButton = (Button)root.findViewById(R.id.button_favorite_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FavoriteSelectActivity.class);
                getActivity().startActivityForResult(intent,REQUEST_FAVORITE);
            }
        });

        String url = "http://8dosanai.com:8888/favorites/";
        NetworkTaskFavorite networkTaskFavorite = new NetworkTaskFavorite(url,null);
        networkTaskFavorite.execute();

        return root;
    }

    public FavoriteViewModel getFavoriteViewModel() {
        return favoriteViewModel;
    }


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
                Log.d("like 확인!", s);
            }
        }

    }
}
package military.discount.info.ui.home;

import android.content.Context;
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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import military.discount.info.R;
import military.discount.info.Shop;
import military.discount.info.ShopList;

public class InfoFragment extends Fragment  {

    private Button likeButton;
    private LatLng markerPosition;
    private Shop shop;
    private TextView textview1;
    private TextView textview2;
    private boolean like;

    public InfoFragment(Shop shop){
        this.shop = shop;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        ShopList shopList = (ShopList)getActivity().getApplicationContext();
        View root = inflater.inflate(R.layout.shop_info, container, false);
        like = false;
        likeButton = (Button)root.findViewById(R.id.like_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(like == false) {
                    Log.d("좋아요", "좋아요 눌림");
                    like = true;
                    likeButton.setBackgroundResource(R.drawable.oval_background_orange_fill);
                }
                else{
                    Log.d("좋아요", "좋아요 취소");
                    like = false;
                    likeButton.setBackgroundResource(R.drawable.oval_background_orange_blank);
                }
            }
        });

        textview1 = (TextView)root.findViewById(R.id.textView2);
        textview2 = (TextView)root.findViewById(R.id.textView3);
        textview1.setText(shop.getName());
        textview2.setText(shop.getAddress());
        return root;
    }

}
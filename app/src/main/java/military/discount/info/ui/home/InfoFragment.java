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
    private TextView name;
    private TextView address;
    private TextView information;
    private TextView phone;
    private TextView url;
    private TextView likes;
    private Button modifyButton;

    private boolean like;

    public InfoFragment(Shop shop){
        this.shop = shop;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        ShopList shopList = (ShopList)getActivity().getApplicationContext();
        View root = inflater.inflate(R.layout.shop_info_view, container, false);
        like = false;

        name = (TextView)root.findViewById(R.id.textView5);
        address = (TextView)root.findViewById(R.id.textView6);
        information =(TextView)root.findViewById(R.id.textView8);
        phone = (TextView)root.findViewById(R.id.textView9);
        url = (TextView)root.findViewById(R.id.textView10);
        likes = (TextView)root.findViewById(R.id.textView7);
        modifyButton = (Button)root.findViewById(R.id.button2);

        name.setText(shop.getName());
        address.setText(shop.getAddress());
        information.setText( "-" + shop.getDescription());
        phone.setText("-" + shop.getPhone());
        url.setText("-" + shop.getPage_url());
        likes.setText(shop.getLikes());
        return root;
    }

}
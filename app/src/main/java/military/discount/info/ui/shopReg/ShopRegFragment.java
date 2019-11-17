package military.discount.info.ui.shopReg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import military.discount.info.R;

public class ShopRegFragment extends Fragment {

    private ShopRegViewModel shopRegViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopRegViewModel =
                ViewModelProviders.of(this).get(ShopRegViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shopreg, container, false);
        final TextView textView = root.findViewById(R.id.text_shopReg);
        shopRegViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
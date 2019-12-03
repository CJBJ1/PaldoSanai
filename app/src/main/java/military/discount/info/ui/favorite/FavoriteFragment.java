package military.discount.info.ui.favorite;

import android.content.Intent;
import android.net.Uri;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import military.discount.info.DividerItemDecoration;
import military.discount.info.R;
import military.discount.info.RecyclerDecoration;
import military.discount.info.SimpleTextAdapter;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private Button deleteButton;
    private Button addButton;
    private int REQUEST_FAVORITE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_item) ;
        favoriteViewModel.setRecyclerView(recyclerView,getContext());

        addButton = (Button)root.findViewById(R.id.button_favorite_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FavoriteSelectActivity.class);
                getActivity().startActivityForResult(intent,REQUEST_FAVORITE);
            }
        });

        return root;
    }

    public FavoriteViewModel getFavoriteViewModel() {
        return favoriteViewModel;
    }

}
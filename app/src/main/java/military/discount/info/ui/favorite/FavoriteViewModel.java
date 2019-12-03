package military.discount.info.ui.favorite;

import android.content.Context;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import military.discount.info.RecyclerDecoration;
import military.discount.info.SimpleTextAdapter;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FavoriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("자주 가는 장소");
    }

    public void setRecyclerView(RecyclerView recyclerView, Context context){
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context)) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter adapter = new SimpleTextAdapter(list) ;
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),R.drawable.line_divider));
        recyclerView.addItemDecoration(new RecyclerDecoration(20));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter) ;
    }

    public LiveData<String> getText() {
        return mText;
    }
}
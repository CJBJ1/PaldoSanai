package military.discount.info.ui.favorite;

import android.app.Activity;
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
    private ArrayList<String> list = new ArrayList<>();
    private SimpleTextAdapter adapter;

    public FavoriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("자주 가는 장소");
    }

    public void setRecyclerView(RecyclerView recyclerView, Context context, Activity activity){
        recyclerView.setLayoutManager(new LinearLayoutManager(context)) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new SimpleTextAdapter(list) ;
        adapter.setContext(context);
        adapter.setActivity(activity);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),R.drawable.line_divider));
        recyclerView.addItemDecoration(new RecyclerDecoration(20));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter) ;
    }

    public LiveData<String> getText() {
        return mText;
    }
    public SimpleTextAdapter getAdapter() {return adapter;}
}
package military.discount.info.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FavoriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("자주 가는 장소");
    }

    public void setRecyclerView(){

    }

    public LiveData<String> getText() {
        return mText;
    }
}
package military.discount.info.ui.shopReg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopRegViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShopRegViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("시설 등록");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
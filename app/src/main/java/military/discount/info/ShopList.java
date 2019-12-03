package military.discount.info;

import android.app.Application;

import java.util.ArrayList;

public class ShopList extends Application {

    private ArrayList<Shop> shopArrayList = new ArrayList<>(500);

    public ArrayList<Shop> getShopArrayList() {
        return shopArrayList;
    }

    public void setShopArrayList(ArrayList<Shop> shopArrayList) {
        this.shopArrayList = shopArrayList;
    }
}

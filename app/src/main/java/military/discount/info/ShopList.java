package military.discount.info;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ShopList extends Application {

    private ArrayList<Shop> shopArrayList = new ArrayList<>(500);
    private LatLng centerPoint = null;

    public ArrayList<Shop> getShopArrayList() {
        return shopArrayList;
    }

    public void setShopArrayList(ArrayList<Shop> shopArrayList) {
        this.shopArrayList = shopArrayList;
    }

    public void setCenterPoint(LatLng latLng) {
        this.centerPoint = latLng;
    }

    public LatLng getCenterPoint(){
        return centerPoint;
    }
}

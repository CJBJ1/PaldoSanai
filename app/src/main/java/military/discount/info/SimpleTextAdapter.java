package military.discount.info;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import military.discount.info.ui.favorite.FavoriteFragment;
import military.discount.info.ui.home.HomeFragment;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private ArrayList<String> mData = null ;
    private ArrayList<LatLng> positions =  new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ShopList shopList;
    private Context context;
    private Activity activity;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button textButton;
        Button deleteButton;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            textButton = itemView.findViewById(R.id.favorite_list_text);
            deleteButton = itemView.findViewById(R.id.button_favorite_delete);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SimpleTextAdapter(ArrayList<String> mData,ArrayList<LatLng> positions,ArrayList<String> id) {
        this.mData = mData ;
        this.positions = positions;
        this.id = id;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.favorite_list, parent, false) ;
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position);
        holder.textButton.setText(text);

        holder.textButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shopList = (ShopList)activity.getApplicationContext();
                shopList.setCenterPoint(positions.get(position));
                activity.onBackPressed();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(position); //or some other task
                positions.remove(position);

                String url = "http://8dosanai.com:8888/favorites/" + id.get(position) + "/";
                NetworkTaskFavoriteDelete networkTaskFavoriteDelete = new NetworkTaskFavoriteDelete(url,null);
                networkTaskFavoriteDelete.execute();

                id.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }


    public ArrayList<String> getmData() {
        return mData;
    }

    public ArrayList<LatLng> getPositions(){
        return positions;
    }

    public ArrayList<String> getId() {return id;}

    public void setContext(Context context){
        this.context = context;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }


    public class NetworkTaskFavoriteDelete extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTaskFavoriteDelete(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            shopList = (ShopList)activity.getApplicationContext();
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            requestHttpURLConnection.setAccessToken(shopList.getAccessToken());
            result = requestHttpURLConnection.requestDelete(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                Log.d("삭제!", s);
            }
        }

    }

}
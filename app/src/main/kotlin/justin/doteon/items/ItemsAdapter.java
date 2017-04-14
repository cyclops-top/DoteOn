package justin.doteon.items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import justin.common.view.BaseRecyclerAdapter;
import justin.common.view.ImageView_Kt;
import justin.doteon.R;
import justin.doteon.model.MovieSubject;

/**
 * @author justin on 2017/04/13 16:51
 * @version V1.0
 */
public class ItemsAdapter extends BaseRecyclerAdapter<MovieSubject> {


    @Override
    public ViewHolder onCreateViewHolder(View item, int viewType) {
        return new ViewHolder(item);
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.items_layout;
    }

    public class ViewHolder extends BaseRecyclerAdapter<MovieSubject>.ViewHolder {
        private final TextView name;
        private final ImageView poster;
        private final TextView rating;
        private final TextView favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) findViewById(R.id.name);
            rating = (TextView) findViewById(R.id.rating);
            favorite = (TextView) findViewById(R.id.favorite);
            poster = (ImageView) findViewById(R.id.poster);
        }

        @Override
        protected void bindData(MovieSubject data) {
            name.setText(data.getTitle());
            if (data.getImages() != null && data.getImages().getLarge() != null) {
                ImageView_Kt.setImageURI(poster, data.getImages().getLarge());
            }else {
                ImageView_Kt.setImageURI(poster, "");
            }
            if(data.getRating() != null&&data.getRating().getAverage()!=0){
                rating.setText(String.format(Locale.ENGLISH,"%.1f",data.getRating().getAverage()));
            }else {
                rating.setText("");
            }
            if(data.getCollect_count() > 0){
                favorite.setVisibility(View.VISIBLE);
                favorite.setText(String.valueOf(data.getCollect_count()));
            }else {
                favorite.setVisibility(View.GONE);
            }
        }
    }


}

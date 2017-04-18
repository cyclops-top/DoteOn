package justin.doteon.detail.views;

import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import justin.common.view.MultiTypeViewHolder;
import justin.doteon.R;
import mvp.BindLayout;

/**
 * @author justin on 2017/04/17 13:50
 * @version V1.0
 */
@BindLayout(R.layout.item_test)
public class ListTestViewHolder extends MultiTypeViewHolder<Integer> {
    TextView mTextView;

    public ListTestViewHolder(@NotNull View itemView) {
        super(itemView);
        mTextView = (TextView) findViewById(R.id.test);
    }

    @Override
    public void bindData(@Nullable Integer data) {
        mTextView.setText("Test#"+data);
    }
}

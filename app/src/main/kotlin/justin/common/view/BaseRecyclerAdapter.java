package justin.common.view;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import justin.common.utils.ListUtils;

/**
 * @author justin on 2017/03/01 14:08
 * @version V1.0
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter<T>.ViewHolder> {

    protected static final int TYPE_ITEM = 0;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<T> list;
    private View headerView;
    private View footerView;

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false);
        return onCreateViewHolder(view, viewType);
    }

    public abstract ViewHolder onCreateViewHolder(View item, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindData(getItem(position));
    }

    public int getHeaderViewCount() {
        return headerView == null ? 0 : 1;
    }

    public int getFooterViewCount() {
        return footerView == null ? 0 : 1;
    }

    public T getItem(int position) {
        return list.get(position - getHeaderViewCount());
    }

    public void addHeader(View header) {
        headerView = header;
        notifyItemInserted(0);
    }

    public void removeHeader() {
        notifyItemRemoved(0);
        headerView = null;
    }

    public void addFooter(View footer) {
        if (footerView != null) {
            removeFooter();
        }
        footerView = footer;
        notifyItemInserted(getHeaderViewCount() + ListUtils.size(list));
    }

    public void removeFooter() {
        notifyItemRemoved(getHeaderViewCount() + ListUtils.size(list));
        footerView = null;
    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + ListUtils.size(list) + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }

    public int getItemSize() {
        return ListUtils.size(list);
    }

    public boolean isItemFullSpan(int position) {
        return getItemViewType(position) != TYPE_ITEM;
    }

    public void addMore(List<T> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        if (list != null) {
            this.list.addAll(list);
        }
    }

    @LayoutRes
    public abstract int getItemLayout(int viewType);

    public List<T> getList() {
        return list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onLongClickListener) {
        this.onItemLongClickListener = onLongClickListener;
    }

    public void removeItem(T item) {
        int pos = list.indexOf(item);
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public void removeItem(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos + getHeaderViewCount());
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (onItemClickListener != null && pos >= 0) {
                    onItemClickListener.onItemClick(v, pos);
                }
            });
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (onItemLongClickListener != null && pos >= 0) {
                    return onItemLongClickListener.onItemLongClick(v, pos);
                }
                return false;
            });
        }

        public View findViewById(@IdRes int id) {
            return itemView.findViewById(id);
        }

        protected abstract void bindData(T data);

    }


}

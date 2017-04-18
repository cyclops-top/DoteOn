package justin.common.view;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import justin.common.utils.ListUtils;
import mvp.BindLayout;

/**
 * @author justin on 2017/04/14 16:42
 * @version V1.0
 */
public class MultiTypeRecyclerAdapter extends RecyclerView.Adapter<MultiTypeViewHolder> {
    private final SparseArray<Class<? extends MultiTypeViewHolder>> viewHolderClass = new SparseArray<>();

    private MultiTypeViewCreator mMultiTypeViewCreator = new MultiTypeViewCreator();
    private List<ItemDataBase<?, ?>> list = new ArrayList<>();
    private int showSize = 0;

    public <T> ItemData<T> add(Class<? extends MultiTypeViewHolder<T>> clazz, T data) {

        int layout = clazz.getAnnotation(BindLayout.class).value();
        viewHolderClass.put(layout, clazz);
        ItemData<T> d = new ItemData<>();
        d.data = data;
        d.clazz = clazz;
        d.layout = layout;
        ItemDataBase lastData = ListUtils.getLast(list);
        d.position = lastData == null ? 0 : lastData.position + lastData.size;
        showSize += d.size;
        list.add(d);
        return d;
    }

    public <T> ListItemData<T> addFromList(Class<? extends MultiTypeViewHolder<T>> clazz, List<T> data) {
        int layout = clazz.getAnnotation(BindLayout.class).value();
        viewHolderClass.put(layout, clazz);
        ListItemData<T> d = new ListItemData<>();
        d.data = data;
        d.clazz = clazz;
        d.layout = layout;
        ItemDataBase lastData = ListUtils.getLast(list);
        d.position = lastData == null ? 0 : lastData.position + lastData.size;
        d.size = ListUtils.size(data);
        showSize += d.size;
        list.add(d);
        return d;
    }


    @Override
    public MultiTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return mMultiTypeViewCreator.createViewHolder(layoutInflater, parent, viewHolderClass.get(viewType), viewType);
    }

    @Override
    public void onBindViewHolder(MultiTypeViewHolder holder, int position) {
        ItemDataBase itemDataBase = getListPosition(position);
        //noinspection unchecked
        holder.setOnItemClickListener(itemDataBase.mOnItemClickListener);
        //noinspection unchecked
        holder.setItemData(itemDataBase,position - itemDataBase.position);
    }

    @Override
    public int getItemViewType(int position) {
        return getListPosition(position).layout;
    }

    @Override
    public int getItemCount() {
        return showSize;
    }

    private ItemDataBase getListPosition(int pos) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            ItemDataBase midVal = list.get(mid);
            int com = midVal.compare(pos);
            if (com < 0) {
                high = mid - 1;
            } else if (com > 0) {
                low = mid + 1;
            } else {

                if (midVal.size > 0) {
                    return midVal;
                }
                int real = mid - 1;
                while (real > low) {
                    ItemDataBase val = list.get(real);
                    if (val.compare(pos) == 0) {
                        if (val.size > 0) {
                            return val;
                        }
                        real--;
                    } else {
                        break;
                    }
                }
                real = mid + 1;
                while (real < high) {
                    ItemDataBase val = list.get(real);
                    if (val.compare(pos) == 0) {
                        if (val.size > 0) {
                            return val;
                        }
                        real++;
                    } else {
                        break;
                    }
                }
            }

        }
        throw new RuntimeException("can not find position " + pos + ".");
    }

    private void refreshPositions(int curr) {
        int listSize = list.size();
        ItemDataBase last = null;
        for (int i = curr; i < listSize; i++) {
            if (last == null) {
                last = list.get(curr);
                continue;
            }
            ItemDataBase data = list.get(curr);
            data.position = last.position + last.size;
            last = data;
        }
    }

    public abstract class ItemDataBase<T, R> {
        T data;
        int layout;
        int size = 1;
        int position;
        Class<? extends MultiTypeViewHolder<R>> clazz;
        OnItemClickListener mOnItemClickListener;
        public abstract void update(T data);

        public abstract void remove();

        protected void setSize(int size) {
            this.size = size;
        }
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public int compare(int pos) {
            if (pos < position) {
                return -1;
            } else if (pos >= position + size) {
                return 1;
            } else {
                return 0;
            }
        }

        public abstract R getData(int offset);
    }


    public class ListItemData<T> extends ItemDataBase<List<T>, T> {

        @Override
        public void update(List<T> data) {
            super.data = data;
            final int newSize = ListUtils.size(data);
            final int oldSize = size;
            if (newSize != oldSize) {
                size = newSize;
                refreshPositions(position);
                showSize += newSize - oldSize;
                notifyItemRangeRemoved(position , oldSize);
                notifyItemRangeInserted(position , newSize);
            }
        }

        @Override
        public void remove() {
            int index = list.indexOf(this);
            list.remove(index);
            showSize -= ListUtils.size(list);
            refreshPositions(position);
            notifyItemRangeRemoved(position, size);
        }

        @Override
        public T getData(int offset) {
            return data.get(offset);
        }
    }

    public class ItemData<T> extends ItemDataBase<T, T> {

        @Override
        public void update(T data) {
            super.data = data;
            notifyItemChanged(position);
        }

        @Override
        public void remove() {
            int index = list.indexOf(this);
            list.remove(index);
            showSize--;
            refreshPositions(position);
            notifyItemRemoved(position);
        }

        @Override
        public T getData(int offset) {
            return data;
        }
    }

    public interface OnItemClickListener<T>{
        void onItemClick(View v,T data);
    }
}

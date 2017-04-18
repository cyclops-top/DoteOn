package justin.common.view;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;

/**
 * @author justin on 2017/04/14 16:50
 * @version V1.0
 */
class MultiTypeViewCreator {

    public MultiTypeViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent,Class<? extends MultiTypeViewHolder> clazz, @LayoutRes int layout) {
        View view = inflater.inflate(layout, parent, false);
        return createViewHolder(clazz,view);
    }

    private   MultiTypeViewHolder createViewHolder(Class<? extends MultiTypeViewHolder> clazz, View view) {
        try {
            return (MultiTypeViewHolder) clazz.getConstructors()[0].newInstance(view);
        } catch (InstantiationException | IllegalAccessException  | InvocationTargetException e) {
            throw new RuntimeException("can not init view holder" +clazz.getName());
        }
    }
}

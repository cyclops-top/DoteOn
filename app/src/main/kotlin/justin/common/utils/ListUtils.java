package justin.common.utils;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author justin on 2017/03/01 14:05
 * @version V1.0
 */
public class ListUtils {
    public static int size(@Nullable List<?> list) {
        return list == null ? 0 : list.size();
    }

    public static boolean isEmpty(@Nullable List<?> list) {
        return size(list) == 0;
    }

    public static String join(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = strings.size(); i < l; i++) {
            sb.append(strings.get(i));
            if (i < l - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static <T> T getLast(List<T> list){
       int size = size(list);
        if(size > 0){
            return list.get(size - 1);
        }
        return null;
    }
}

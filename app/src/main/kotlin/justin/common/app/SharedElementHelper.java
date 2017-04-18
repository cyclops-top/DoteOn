package justin.common.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;

/**
 * @author justin on 2017/03/15 15:12
 * @version V1.0
 */
public class SharedElementHelper {
    public static Bundle makeSceneTransitionAnimation(Activity activity, @NonNull View... shares) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(shares.length == 0){
                return ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
            }
            Pair[] scenes = new Pair[shares.length];
            int index = 0;
            for (View v : shares) {
                String transitionName = v.getTransitionName();
                if (TextUtils.isEmpty(transitionName)) {
                    throw new RuntimeException("this view must be set transition name " + v.toString());
                }
                Pair<View, String> pair = new Pair<>(v, transitionName);
                scenes[index] = pair;
                index++;
            }
            //noinspection unchecked
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, scenes).toBundle();
        } else {
            return new Bundle();
        }

    }
}

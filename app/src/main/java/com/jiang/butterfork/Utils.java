package com.jiang.butterfork;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by knowing on 2017/11/10.
 */

public final class Utils {

    public static <T> T findViewAndCost(View source, @IdRes int id, Class<T> cls) {
        View view = source.findViewById(id);
        return cls.cast(view);
    }

}

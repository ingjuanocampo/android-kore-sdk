package kore.botssdk.view.viewUtils;

import android.content.Context;

import kore.botssdk.utils.Utils;

/**
 * Created by Pradeep Mahato on 31-May-16.
 */
public class DimensionUtil {

    Context context;
    public static float dp1;

    public DimensionUtil(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        dp1 = Utils.convertDpToPixel(context, 1);
    }

}
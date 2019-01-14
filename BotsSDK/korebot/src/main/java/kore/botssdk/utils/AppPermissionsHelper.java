package kore.botssdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


/**
 * Created by Ramachandra on 03/12/16.
 */
public class AppPermissionsHelper {

    public static void requestForPermission(Activity activity, String permission, int requestCode) {
        if (shouldShowRequestPermissionRationale(activity, permission)) {
            requestPermissions(activity, new String[]{permission},
                    requestCode);
        } else {
            requestPermissions(activity, new String[]{permission},
                    requestCode);
        }
    }
    public static boolean hasPermission(Context context, String... permission) {
        boolean shouldShowRequestPermissionRationale = true;
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionLength = permission.length;
            for (int i=0;i<permissionLength;i++) {
                shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale &&
                        checkSelfPermission(context, permission[i]) == PackageManager.PERMISSION_GRANTED;
            }
        }
        return shouldShowRequestPermissionRationale;
    }
    /**
     *
     * @param activity : For which activity
     * @param requestCode : The user's request code
     * @param permission : List of desired permissions.
     */
    public static void requestForPermission(Activity activity, int requestCode, String... permission) {
        boolean shouldShowRequestPermissionRationale = shouldShowRationale(activity, permission);

        if (shouldShowRequestPermissionRationale) {
            requestPermissions(activity, permission,
                    requestCode);
        } else {
            requestPermissions(activity, permission,
                    requestCode);
        }
    }

    public static boolean shouldShowRationale(Activity activity, String... permission) {
        boolean shouldShowRequestPermissionRationale = false;
        int permissionLength = permission.length;
        for (int i=0;i<permissionLength;i++) {
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || shouldShowRequestPermissionRationale(activity, permission[i]);
        }
        return shouldShowRequestPermissionRationale;
    }

    public static void requestForPermission(Activity activity, String permission[], int requestCode) {
        if (shouldShowRequestPermissionRationale(activity, permission[0])) {
            requestPermissions(activity, permission,
                    requestCode);
        } else {
            requestPermissions(activity, permission,
                    requestCode);
        }
    }



    public static void startInstalledAppDetailsActivity(final Activity activity) {
        if (activity == null) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }
}

package com.mapua.aquajmt.customerapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by Bryan on 5/27/2017.
 */

public class VectorDrawableUtils {

    public static Bitmap createBitmap(Resources resource, int resourceId, Resources.Theme theme) {
        Drawable drawable = resource.getDrawable(resourceId, theme);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static BitmapDescriptor createBitmapDescriptor(Resources resource, int resourceId, Resources.Theme theme) {
        return BitmapDescriptorFactory.fromBitmap(createBitmap(resource, resourceId, theme));
    }
}

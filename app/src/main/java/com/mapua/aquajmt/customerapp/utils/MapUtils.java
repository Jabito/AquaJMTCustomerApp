package com.mapua.aquajmt.customerapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.mapua.aquajmt.customerapp.R;

/**
 * Created by medicardphilippines on 02/06/2017.
 */

public class MapUtils {
    public static Bitmap getMarkerBitmapFromView(Activity activity, @DrawableRes int resId) {
        View view = activity.getLayoutInflater().inflate(R.layout.store_badge_layout, null);
        ImageView storeImageView = (ImageView) view.findViewById(R.id.img_store);

        Bitmap storeImage = BitmapFactory.decodeResource(activity.getResources(), resId);
        RoundedBitmapDrawable storeImageDrawable = RoundedBitmapDrawableFactory
                .create(activity.getResources(), storeImage);
        storeImageDrawable.setCircular(true);
        storeImageView.setImageDrawable(storeImageDrawable);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable != null) {
            backgroundDrawable.draw(canvas);
        }
        view.draw(canvas);

        return bitmap;
    }
}

package com.qeevee.ui;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.WindowManager;

public class BitmapUtil {
	 
	public static Bitmap scaleBitmapToScreenWidth(Bitmap origBitmap,
			Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int newWidth = wm.getDefaultDisplay().getWidth();
		float scaleBy = ((float) newWidth) / origBitmap.getWidth();
		Matrix matrix = new Matrix();
		matrix.postScale(scaleBy, scaleBy);
		return Bitmap.createBitmap(origBitmap, 0, 0, origBitmap.getWidth(),
				origBitmap.getHeight(), matrix, true);
	}

	public static Bitmap readBitmapFromFile(File file, Context context) {
//		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//		DisplayMetrics displayMetrics = new DisplayMetrics();
//		wm.getDefaultDisplay().getMetrics(displayMetrics);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
//		options.inDensity = displayMetrics.densityDpi;
//		options.inScreenDensity = displayMetrics.densityDpi;
//		options.inTargetDensity = displayMetrics.densityDpi;
//		options.inScaled = true;

		String filePath = file.getAbsolutePath();
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		if (bitmap == null) {
			Log.d(BitmapUtil.class.getCanonicalName(),
					"Could not decode bitmap from file "
							+ file.getAbsolutePath());
			bitmap = BitmapFactory.decodeResource(context.getResources(), com.qeevee.util.R.drawable.missingbitmap);
		}
		return (bitmap);
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}

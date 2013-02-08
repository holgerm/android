package com.qeevee.gq.tests.robolectric;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowBitmap;

@Implements(Bitmap.class)
public class ShadowGQBitmap extends ShadowBitmap {

    /**
     * @param source
     * @param x
     * @param y
     * @param width
     * @param height
     * @param m
     * @param filter
     * @return the unchanged source bitmap ;-)
     */
    public static Bitmap createBitmap(Bitmap source,
				      int x,
				      int y,
				      int width,
				      int height,
				      Matrix m,
				      boolean filter) {
	return source;
    }

}

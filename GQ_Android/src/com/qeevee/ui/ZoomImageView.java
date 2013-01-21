package com.qeevee.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ZoomImageView extends ImageView {

	public ZoomImageView(Context context) {
		super(context);
		addZoomListener(context);
	}

	private void addZoomListener(final Context context) {
		setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent fullScreenIntent = new Intent(v.getContext(),
						FullScreenImage.class);
				Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
				bitmap = BitmapUtil.scaleBitmapToScreenWidth(bitmap, context);
				fullScreenIntent.putExtra("bitmap", bitmap);
				context.startActivity(fullScreenIntent);
			}
		});
	}

	public ZoomImageView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		addZoomListener(context);
	}

	public ZoomImageView(Context context, AttributeSet attributeSet,
			int defStyle) {
		super(context, attributeSet, defStyle);
		addZoomListener(context);
	}

}

package edu.bonn.mobilegaming.geoquest.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MediaController extends android.widget.MediaController {

    private static final String TAG = "MediaController (GQ)";

    Button b;

    public MediaController(Context context) {
	super(context);
	b = new Button(context);
	b.setText("Test");
	b.setVisibility(VISIBLE);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
	// TODO Auto-generated method stub
	return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	return super.onTouchEvent(event);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
	// TODO Auto-generated method stub
	super.onWindowVisibilityChanged(visibility);

	try {
	    if (visibility == View.VISIBLE) {
		LinearLayout hll = (LinearLayout) getChildAt(0);
		LinearLayout bll = (LinearLayout) hll.getChildAt(0);
		if (b.getParent() != bll) {
		    bll.addView(b);
		    invalidate();
		}
	    }
	} catch (Exception e) {
	    Log.e(TAG,
		  e.toString());
	}
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
	// TODO Auto-generated method stub
	return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus,
				  int direction,
				  Rect previouslyFocusedRect) {
	// TODO Auto-generated method stub
	super.onFocusChanged(gainFocus,
			     direction,
			     previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
	// TODO Auto-generated method stub
	super.onWindowFocusChanged(hasWindowFocus);
    }

}

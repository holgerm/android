package com.qeevee.gq.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.Start;

@RunWith(RobolectricTestRunner.class)
public class ResourceTests {
	
	Activity start;
	
	@Before
	public void setUp() {
		start = new Start();
	}

	@Test
	public void testLogo() {
		assertNotNull(start.getResources().getDrawable(R.drawable.icon));
		BitmapDrawable drawable = (BitmapDrawable) start.getResources().getDrawable(R.drawable.icon);
		assertEquals(100, drawable.getBitmap().getHeight());
		assertEquals(100, drawable.getBitmap().getWidth());
	}

}

package edu.bonn.mobilegaming.geoquest.test;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import edu.bonn.mobilegaming.geoquest.Start;
import edu.bonn.mobilegaming.geoquest.views.GeoquestButton;

public class TestGQSTartActivity extends
		ActivityInstrumentationTestCase2<Start> {

	private Start mActivity;

	public TestGQSTartActivity() {
		super("edu.bonn.mobilegaming.geoquest", Start.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = this.getActivity();
	}

	public void testLogoAppears() {
		ImageView imageView = (ImageView) mActivity
				.findViewById(edu.bonn.mobilegaming.geoquest.R.id.start_image_logo);
		assertNotNull(
				"Start Activity should contain edu.bonn.mobilegaming.geoquest.R.id.start_image_logo",
				imageView);
		assertEquals("Logo should be visible at start", ImageView.VISIBLE,
				imageView.getVisibility());
	}

	public void testLastPlayedButtonText() {
		GeoquestButton lastPlayedButton = (GeoquestButton) mActivity
				.findViewById(edu.bonn.mobilegaming.geoquest.R.id.start_button_last_game);
		if (lastPlayedButton.isEnabled()) {
			assertTrue(lastPlayedButton.getText() != null
					&& !lastPlayedButton.getText().equals(""));
		} else {
			assertTrue(lastPlayedButton
					.getText()
					.equals(mActivity
							.getText(edu.bonn.mobilegaming.geoquest.R.string.start_text_last_game_text_no_game))
					|| lastPlayedButton
							.getText()
							.equals(mActivity
									.getText(edu.bonn.mobilegaming.geoquest.R.string.start_gameNotFound)));
		}
	}

	public void testSelectOtherGamesButton() {
		GeoquestButton selectOtherGamesButton = (GeoquestButton) mActivity
				.findViewById(edu.bonn.mobilegaming.geoquest.R.id.start_button_game_list);
		assertNotNull(selectOtherGamesButton);
		assertEquals(
				"Select Other Games Button does NOT show the right text",
				mActivity
						.getText(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text),
				selectOtherGamesButton.getText());
		assertEquals(true, selectOtherGamesButton.isEnabled());
	}
	
}

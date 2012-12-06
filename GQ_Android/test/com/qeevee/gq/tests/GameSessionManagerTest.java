package com.qeevee.gq.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import edu.bonn.mobilegaming.geoquest.Start;

@RunWith(RobolectricTestRunner.class)
public class GameSessionManagerTest {

    @Test
    public void onGameStartSessionManagerShouldBeCalledToSetSessionID() {
	Start start = TestUtils.startGameForTest("GameSessionManagerTest");
	
    }


}

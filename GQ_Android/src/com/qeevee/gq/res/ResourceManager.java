package com.qeevee.gq.res;

import android.os.Environment;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.Globals;

public class ResourceManager {

    public static String getResourcePath(String specifiedPath) {

	if (specifiedPath.startsWith(Globals.RUNTIME_RESOURCE_PREFIX)) {
	    String filepath = specifiedPath.substring(Globals.RUNTIME_RESOURCE_PREFIX
		    .length());
	    return Environment.getExternalStorageDirectory().getAbsolutePath()
		    + "/" + filepath;
	}

	else {
	    return GeoQuestApp.getGameRessourceFile(specifiedPath)
		    .getAbsolutePath();
	}
    }

}

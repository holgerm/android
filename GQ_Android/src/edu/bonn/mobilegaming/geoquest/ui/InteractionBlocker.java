package edu.bonn.mobilegaming.geoquest.ui;

import edu.bonn.mobilegaming.geoquest.BlockableAndReleasable;
import edu.bonn.mobilegaming.geoquest.MissionOrToolActivity;

/**
 * Instances of InteractionBlocker are responsible for blocking and releasing
 * interactive objects, such as {@link MissionOrToolActivity}.
 * <p/>
 * Hence they have to call their method
 * {@link MissionOrToolActivity#blockInteraction(InteractionBlocker)}
 * to block and their method {@link BlockableAndReleasable#release()} for
 * releasing it again.
 * 
 * @author muegge
 * 
 */
public interface InteractionBlocker {

}

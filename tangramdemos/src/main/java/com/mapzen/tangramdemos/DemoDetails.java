package com.mapzen.tangramdemos;

import androidx.appcompat.app.AppCompatActivity;

public class DemoDetails {

    private final int titleId;

    private final int detailId;

    private final Class<? extends AppCompatActivity> activityClass;

    /**
     * Create object to represent a demoable component of the sdk.
     *
     * @param titleId title of demo
     * @param detailId details about demo
     * @param activityClass activity to launch when this list item is clicked
     */
    public DemoDetails(int titleId, int detailId, Class activityClass) {
        this.titleId = titleId;
        this.detailId = detailId;
        this.activityClass = activityClass;
    }

    /**
     * Resource id for title string.
     */
    public int getTitleId() {
        return titleId;
    }

    /**
     * Resource id for detail string.
     */
    public int getDetailId() {
        return detailId;
    }

    /**
     * Activity class to launch when this demo item is selected from list.
     */
    public Class<? extends AppCompatActivity> getActivityClass() {
        return activityClass;
    }

    public static final DemoDetails[] LIST = {
            new DemoDetails(R.string.simplemap_title, R.string.simplemap_detail, SimpleMapActivity.class),
            new DemoDetails(R.string.featurepicking_title, R.string.featurepicking_detail, FeaturePickingActivity.class),
            new DemoDetails(R.string.mapgestures_title, R.string.mapgestures_detail, MapGesturesActivity.class),
            new DemoDetails(R.string.mapmovement_title, R.string.mapmovement_detail, MapMovementActivity.class),
            new DemoDetails(R.string.markers_title, R.string.markers_detail, MarkersActivity.class),
            new DemoDetails(R.string.multimap_title, R.string.multimap_detail, MultiMapActivity.class),
            new DemoDetails(R.string.sceneupdates_title, R.string.sceneupdates_detail, SceneUpdatesActivity.class),
    };
}

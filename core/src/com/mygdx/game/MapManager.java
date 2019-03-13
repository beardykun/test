package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapLayer;
import com.mygdx.game.profile.ProfileManager;
import com.mygdx.game.profile.ProfileObserver;

import java.util.Map;

import javax.swing.text.html.parser.Entity;

public class MapManager implements ProfileObserver {
    private static final String TAG = MapManager.class.getSimpleName();

    private Camera camera;
    private boolean mapChanged = false;
    private Map currentMap;
    private Entity player;
    private Entity currentSelectedEntity = null;
    private MapLayer currentLightMap = null;
    private MapLayer previousLightMap = null;
    private ClockActor.TimeOfDay timeOfDay = null;
    private float currentLightMapOpacity = 0;
    private float previousLightMapOpacity = 1;
    private boolean timeOfDayChanged = false;

    public MapManager(){}

    @Override
    public void onNotify(ProfileManager profileManager, ProfileEvent event) {
        switch (event){
            case PROFILE_LOADED:
                String currentMap = profileManager.getProperty("currentMapType", String.class);
                MapFactory.MapType mapType;
        }
    }
}

package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.audio.AudioSubject;

import java.util.Hashtable;

public class Map implements AudioSubject {
    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE = 1 / 16f;

    private final static String COLLISION_LAYER = "MAP_COLLISION_LAYER";
    private final static String SPAWNS_LAYER = "MAP_SPAWNS_LAYER";
    private final static String PORTAL_LAYER = "MAP_PORTAL_LAYER";
    private final static String QUEST_ITEM_SPAWN_LAYER = "MAP_QUEST_ITEM_SPAWN_LAYER";
    private final static String QUEST_DISCOVER_LAYER = "MAP_QUEST_DISCOVER_LAYER";
    private final static String ENEVY_SPAWN_LAYER = "MAP_ENEMY_SPAWN_LAYER";
    private final static String PARTICLE_EFFECT_SPAWN_LAYER = "PARTICLE_EFFECT_SPAWN_LAYER";

    public final static String BACKGROUND_LAYER = "BACKGROUND_LAYER";
    public final static String GROUND_LAYER = "GROUND_LAYER";
    public final static String DECORATION_LAYER = "DECORATION_LAYER";

    public final static String LIGHT_MAP_DAWN_LAYER = "MAP_LIGHT_MAP_LAYER_DAWN";
    public final static String LIGHT_MAP_LAYER_AFTERNOON = "MAP_LIGHT_MAP_LAYER_AFTERNOON";
    public final static String LIGHT_MAP_DUSK_LAYER = "MAP_LIGHT_MAP_LAYER_DUSK";
    public final static String LIGHT_MAP_NIGHT_LAYER = "MAP_LIGHT_MAP_LAYER_NIGHT";

    private final static String PLAYER_START = "PLAYER_START";
    private final static String NPC_START = "NPC_START";

    protected Json json;

    private Vector2 playerStartPositionRect;
    private Vector2 closestPlayerStartPosition;
    private Vector2 convertedUnits;
    private TiledMap currentMap = null;
    private Vector2 playerStert;
    private Array<Vector2> npcStartPositions;
    private Hashtable<String, Vector2> specialNPCStartPositions;

    private MapLayer collisionLayer = null;
    private MapLayer portalLayer = null;
    private MapLayer spawnLayer = null;
    private MapLayer _questItemSpawnLayer = null;
    private MapLayer _questDiscoverLayer = null;
    private MapLayer _enemySpawnLayer = null;
    private MapLayer _particleEffectSpawnLayer = null;

    @Override
    public void addObserver(AudioObserver audioObserver) {

    }

    @Override
    public void removeObserver(AudioObserver audioObserver) {

    }

    @Override
    public void removeAllObservers() {

    }

    @Override
    public void notify(AudioObserver.AudioCommand command, AudioObserver.AudioTypeEvent event) {

    }
}

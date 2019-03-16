package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.audio.AudioSubject;
import com.mygdx.game.sfx.ParticleEffectFactory;

import java.util.Hashtable;

import javax.swing.text.html.parser.Entity;

public abstract class Map implements AudioSubject {
    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE = 1 / 16f;

    private Array<AudioObserver> observers;

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
    public final static String LIGHT_MAP_AFTERNOON_LAYER = "MAP_LIGHT_MAP_LAYER_AFTERNOON";
    public final static String LIGHT_MAP_DUSK_LAYER = "MAP_LIGHT_MAP_LAYER_DUSK";
    public final static String LIGHT_MAP_NIGHT_LAYER = "MAP_LIGHT_MAP_LAYER_NIGHT";

    private final static String PLAYER_START = "PLAYER_START";
    private final static String NPC_START = "NPC_START";

    protected Json json;

    private Vector2 playerStartPositionRect;
    private Vector2 closestPlayerStartPosition;
    private Vector2 convertedUnits;
    private TiledMap currentMap = null;
    private Vector2 playerStart;
    private Array<Vector2> npcStartPositions;
    private Hashtable<String, Vector2> specialNPCStartPositions;

    private MapLayer collisionLayer = null;
    private MapLayer portalLayer = null;
    private MapLayer spawnLayer = null;
    private MapLayer questItemSpawnLayer = null;
    private MapLayer questDiscoverLayer = null;
    private MapLayer enemySpawnLayer = null;
    private MapLayer particleEffectSpawnLayer = null;

    private MapLayer lightMapDawnLayer = null;
    private MapLayer lightMapAfternoonLayer = null;
    private MapLayer lightMapDuskLayer = null;
    private MapLayer lightMapNightLayer = null;

    private MapFactory.MapType currentMapType;
    private Array<Entity> mapEntities;
    private Array<Entity> mapQuestEntities;
    private Array<ParticleEffect> mapParticleEffects;

    Map(MapFactory.MapType mapType, String fullMapPath) {
        json = new Json();
        mapEntities = new Array<Entity>(10);
        observers = new Array<AudioObserver>();
        mapQuestEntities = new Array<Entity>();
        mapParticleEffects = new Array<ParticleEffect>();
        currentMapType = mapType;
        playerStart = new Vector2(0, 0);
        playerStartPositionRect = new Vector2(0, 0);
        closestPlayerStartPosition = new Vector2(0, 0);
        convertedUnits = new Vector2(0, 0);

        if (fullMapPath == null || fullMapPath.isEmpty()) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        }

        Utility.loadMapAsset(fullMapPath);
        if (Utility.isAssetLoaded(fullMapPath)) {
            currentMap = Utility.getMapAsset(fullMapPath);
        } else {
            Gdx.app.debug(TAG, "Map not loaded");
            return;
        }

        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER);
        if (collisionLayer == null) {
            Gdx.app.debug(TAG, "No collision layer!");
        }
        portalLayer = currentMap.getLayers().get(PORTAL_LAYER);
        if (portalLayer == null) {
            Gdx.app.debug(TAG, "No portal layer");
        }
        spawnLayer = currentMap.getLayers().get(SPAWNS_LAYER);
        if (spawnLayer == null) {
            Gdx.app.debug(TAG, "No spawn layer!");
        }else {
            setClosestStartPosition(playerStart);
        }
        questItemSpawnLayer = currentMap.getLayers().get(QUEST_ITEM_SPAWN_LAYER);
        if (questItemSpawnLayer == null) {
            Gdx.app.debug(TAG, "No quest item spawn layer!");
        }
        questDiscoverLayer = currentMap.getLayers().get(QUEST_DISCOVER_LAYER);
        if (questDiscoverLayer == null) {
            Gdx.app.debug(TAG, "No quest discover layer!");
        }
        enemySpawnLayer = currentMap.getLayers().get(ENEVY_SPAWN_LAYER);
        if (enemySpawnLayer == null) {
            Gdx.app.debug(TAG, "No enemy spawn layer!");
        }
        lightMapDawnLayer = currentMap.getLayers().get(LIGHT_MAP_DAWN_LAYER);
        if (lightMapDawnLayer == null) {
            Gdx.app.debug(TAG, "No dawn lightmap layer found!");
        }
        lightMapAfternoonLayer = currentMap.getLayers().get(LIGHT_MAP_AFTERNOON_LAYER);
        if (lightMapAfternoonLayer == null) {
            Gdx.app.debug(TAG, "No afternoon lightmap layer found!");
        }
        lightMapDuskLayer = currentMap.getLayers().get(LIGHT_MAP_DUSK_LAYER);
        if (lightMapDuskLayer == null) {
            Gdx.app.debug(TAG, "No dusk lightmap layer found!");
        }
        lightMapNightLayer = currentMap.getLayers().get(LIGHT_MAP_NIGHT_LAYER);
        if (lightMapNightLayer == null) {
            Gdx.app.debug(TAG, "No night lightmap layer found!");
        }
        particleEffectSpawnLayer = currentMap.getLayers().get(PARTICLE_EFFECT_SPAWN_LAYER);
        if (particleEffectSpawnLayer == null) {
            Gdx.app.debug(TAG, "No particle effect spawn layer!");
        }
        npcStartPositions = getNPCStartPosition();
        specialNPCStartPositions = getSpecialNPCStartPosition();

        this.addObserver(AudioManager.getInstance());
    }

    public MapLayer getLightMapDawnLayer() {
        return lightMapDawnLayer;
    }

    public MapLayer getLightMapAfternoonLayer() {
        return lightMapAfternoonLayer;
    }

    public MapLayer getLightMapDuskLayer() {
        return lightMapDuskLayer;
    }

    public MapLayer getLightMapNightLayer() {
        return lightMapNightLayer;
    }

    Array<Vector2> getParticleEffectSpawnPositions(ParticleEffectFactory.ParticleEffectType particleEffectType) {
        Array<Vector2> positions = new Array<Vector2>();

        for (MapObject object : particleEffectSpawnLayer.getObjects()) {
            String name = object.getName();

            if (name == null || name.isEmpty() || name.equalsIgnoreCase(particleEffectType.toString())) {
                continue;
            }

            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            float x = rect.getX() + (rect.width / 2);
            float y = rect.getY() + (rect.height / 2);

            x *= UNIT_SCALE;
            y *= UNIT_SCALE;

            positions.add(new Vector2(x, y));
        }
        return positions;
    }

    Array<Vector2> getQuestItemSpawnPositions(String objectName, String objectTaskID) {
        Array<Vector2> positions = new Array<Vector2>();

        for (MapObject object : questItemSpawnLayer.getObjects()) {
            String name = object.getName();
            String taskID = (String) object.getProperties().get("taskID");

            if (name == null || taskID == null || !name.equalsIgnoreCase(objectName) || !taskID.equalsIgnoreCase(objectTaskID)) {
                continue;
            }
            float x = ((RectangleMapObject) object).getRectangle().getX();
            float y = ((RectangleMapObject) object).getRectangle().getY();

            x *= UNIT_SCALE;
            y *= UNIT_SCALE;
            positions.add(new Vector2(x, y));
        }
        return positions;
    }

    public Array<Entity> getMapEntities() {
        return mapEntities;
    }

    public Array<Entity> getMapQuestEntities() {
        return mapQuestEntities;
    }

    public Array<ParticleEffect> getMapParticleEffects() {
        return mapParticleEffects;
    }

    public void addMapQuestEntities(Array<Entity> entities) {
        mapQuestEntities.addAll(entities);
    }

    public MapFactory.MapType getCurrentMapType() {
        return currentMapType;
    }

    public static String getPlayerStart() {
        return PLAYER_START;
    }

    public void setPlayerStart(Vector2 playerStart) {
        this.playerStart = playerStart;
    }

    protected void updateMapEntities(MapManager mapMgr, Batch batch, float delta){
        for (int i = 0; i < mapEntities.size; i++){
            mapEntities.get(i).update(mapMgr, batch, delta);
        }
        for (int i = 0; i < mapQuestEntities.size; i++){
            mapQuestEntities.get(i).update(mapMgr, batch, delta);
        }
    }

    protected void updateMapEffects(Batch batch, float delta){
        //todo check if ok
        batch.begin();
        for (int i = 0; i < mapParticleEffects.size; i++){
            mapParticleEffects.get(i).draw(batch, delta);
        }
        batch.end();
    }

    protected void dispose(){
        for (int i = 0; i < mapEntities.size; i++){
            mapEntities.get(i).dispose();
        }
        for (int i = 0; i < mapQuestEntities.size; i++){
            mapQuestEntities.get(i).dispose();
        }
        for (int i = 0; i < mapParticleEffects.size; i++){
            mapParticleEffects.get(i).dispose();
        }
    }

    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }

    public MapLayer getPortalLayer() {
        return portalLayer;
    }

    public MapLayer getQuestItemSpawnLayer() {
        return questItemSpawnLayer;
    }

    public MapLayer getQuestDiscoverLayer() {
        return questDiscoverLayer;
    }

    public MapLayer getEnemySpawnLayer() {
        return enemySpawnLayer;
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public Vector2 getPlayerStartUnitScaled(){
        Vector2 playerStart = this.playerStart.cpy();
        playerStart.set(this.playerStart.x * UNIT_SCALE, this.playerStart.y * UNIT_SCALE);
        return playerStart;
    }

    public Array<Vector2> getNpcStartPositions() {
        Array<Vector2> npcStartPositions = new Array<Vector2>();

        for (MapObject object : spawnLayer.getObjects()){
            String objectName = object.getName();

            if (objectName == null || objectName.isEmpty()){
                continue;
            }
            if (objectName.equalsIgnoreCase(NPC_START)){
                float x = ((RectangleMapObject)object).getRectangle().getX();
                float y = ((RectangleMapObject)object).getRectangle().getY();

                x *= UNIT_SCALE;
                y *= UNIT_SCALE;

                npcStartPositions.add(new Vector2(x, y));
            }
        }
        return npcStartPositions;
    }

    private Hashtable<String, Vector2> getSpecialNPCStartPositions(){
        Hashtable<String, Vector2> specialNPCStartPositions = new Hashtable<String, Vector2>();
    }

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

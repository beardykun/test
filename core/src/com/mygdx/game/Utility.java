package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Utility {

    public static final AssetManager assetManager = new AssetManager();
    private static final String TAG = Utility.class.getSimpleName();
    private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

    private static final String STATUS_UI_TEXTURE_ATLAS_PATH = "skins/statusui.atlas";
    private final static String STATUSUI_SKIN_PATH = "skins/statusui.json";
    private final static String ITEMS_TEXTURE_ATLAS_PATH = "skins/items.atlas";
    private final static String ITEMS_SKIN_PATH = "skins/items.json";

    public static TextureAtlas STATUS_UI_TEXTURE_ATLAS = new TextureAtlas(STATUS_UI_TEXTURE_ATLAS_PATH);
    public static TextureAtlas ITEMS_TEXTURE_ATLAS = new TextureAtlas(ITEMS_TEXTURE_ATLAS_PATH);
    public static Skin STATUS_UI_SKIN = new Skin(Gdx.files.internal(STATUSUI_SKIN_PATH), STATUS_UI_TEXTURE_ATLAS);

    public static void unloadAsset(String assetsFilenamePath) {
        if (assetManager.isLoaded(assetsFilenamePath)) {
            assetManager.unload(assetsFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload: " + assetsFilenamePath);
        }
    }

    public static float loadComliated() { return assetManager.getProgress(); }

    public static int numberAssetsQueued() {
        return assetManager.getQueuedAssets();
    }

    public static boolean updateAssetLoading() {
        return assetManager.update();
    }

    public static boolean isAssetLoaded(String fileName) {
        return assetManager.isLoaded(fileName);
    }

    public static void loadMapAsset(String mapFilenamePath) {
        if (mapFilenamePath == null || mapFilenamePath.isEmpty()) {
            return;
        }
        if (assetManager.isLoaded(mapFilenamePath)) {
            return;
        }
        if (filePathResolver.resolve(mapFilenamePath).exists()) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));
            assetManager.finishLoadingAsset(mapFilenamePath);
            Gdx.app.debug(TAG, "Map loaded!: " + mapFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Map doesn't exist!: " + mapFilenamePath);
        }
    }

    public static TiledMap getMapAsset(String mapFilenamePath) {
        TiledMap map = null;

        if (assetManager.isLoaded(mapFilenamePath)) {
            map = assetManager.get(mapFilenamePath, TiledMap.class);
        } else {
            Gdx.app.debug(TAG, "Map is not loaded: " + mapFilenamePath);
        }
        return map;
    }

    public static void loadSoundAsset(String soundFilenamePath) {
        if (soundFilenamePath == null || soundFilenamePath.isEmpty()) {
            return;
        }
        if (assetManager.isLoaded(soundFilenamePath)) {
            return;
        }
        if (filePathResolver.resolve(soundFilenamePath).exists()) {
            assetManager.setLoader(Sound.class, new SoundLoader(filePathResolver));
            assetManager.load(soundFilenamePath, Sound.class);
            assetManager.finishLoadingAsset(soundFilenamePath);
            Gdx.app.debug(TAG, "Sound loaded!: " + soundFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Sound doesn't exist!: " + soundFilenamePath);
        }
    }

    public static Sound getSounAsset(String soundFilenamePath) {
        Sound sound = null;

        if (assetManager.isLoaded(soundFilenamePath)) {
            sound = assetManager.get(soundFilenamePath, Sound.class);
        } else {
            Gdx.app.debug(TAG, "Sound is not loaded: " + soundFilenamePath);
        }
        return sound;
    }

    public static void loadMusicAsset(String musicFilenamePath) {
        if (musicFilenamePath == null || musicFilenamePath.isEmpty()) {
            return;
        }
        if (assetManager.isLoaded(musicFilenamePath)) {
            return;
        }
        if (filePathResolver.resolve(musicFilenamePath).exists()) {
            assetManager.setLoader(Music.class, new MusicLoader(filePathResolver));
            assetManager.load(musicFilenamePath, Music.class);
            assetManager.finishLoadingAsset(musicFilenamePath);
            Gdx.app.debug(TAG, "Sound loaded!: " + musicFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Sound doesn't exist!: " + musicFilenamePath);
        }
    }

    public static Music getMusicAsset(String musicFilenamePath) {
        Music music = null;

        if (assetManager.isLoaded(musicFilenamePath)) {
            music = assetManager.get(musicFilenamePath, Music.class);
        } else {
            Gdx.app.debug(TAG, "Music is not loaded: " + musicFilenamePath);
        }
        return music;
    }

    public static void loadTextureAsset(String textureFilenamePath) {
        if (textureFilenamePath == null || textureFilenamePath.isEmpty()) {
            return;
        }
        if (assetManager.isLoaded(textureFilenamePath)) {
            return;
        }
        if (filePathResolver.resolve(textureFilenamePath).exists()) {
            assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));
            assetManager.load(textureFilenamePath, Texture.class);
            assetManager.finishLoadingAsset(textureFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Texture doesn't exist!: " + textureFilenamePath);
        }
    }

    public static Texture getTextureAsset(String textureFilenamePath) {
        Texture texture = null;
        if (assetManager.isLoaded(textureFilenamePath)) {
            texture = assetManager.get(textureFilenamePath, Texture.class);
        } else {
            Gdx.app.debug(TAG, "Texture is not loaded: " + textureFilenamePath);
        }
        return texture;
    }
}



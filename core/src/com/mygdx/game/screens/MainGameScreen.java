package com.mygdx.game.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.MapManager;
import com.mygdx.game.MyGdxGame;

import javax.swing.text.html.parser.Entity;

public class MainGameScreen extends GameScreen {
    private static final String TAG = MainGameScreen.class.getSimpleName();

    private static class VIEWPORT{
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    public enum GameState{
        SAVING,
        LOADING,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private static GameState gameState;

    OrthogonalTiledMapRenderer mapRenderer = null;
    MapManager mapMgr;
    OrthographicCamera camera;
    OrthographicCamera hudCamera;

    private Json json;
    private MyGdxGame game;
    private InputMultiplexer multiplexer;

    private Entity player;
    private PlayerHUD playerHUD;

    public MainGameScreen(MyGdxGame game){
        this.game = game;
        mapMgr = new MapManager();
        json = new Json();

        setGameState(GameState.RUNNING);
        setupViwport(10, 10);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
    }
}

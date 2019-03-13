package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.screens.CreditScreen;
import com.mygdx.game.screens.CutSceneScreen;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.LoadGameScreen;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.NewGameScreen;

public class MyGdxGame extends Game {

    private static MainGameScreen sMainGameScreen;
    private static NewGameScreen sNewGameScreen;
    private static GameOverScreen sGameOverScreen;
    private static MainMenuScreen sMainMenuScreen;
    private static LoadGameScreen sLoadGameScreen;
    private static CutSceneScreen sCutSceneScreen;
    private static CreditScreen sCreditScreen;

    public enum ScreenType {
        MainMenu,
        LoadGame,
        MainGame,
        Credits,
        NewGame,
        GameOver,
        WatchIntro
    }

    public Screen getScreenType(ScreenType screenType) {
        switch (screenType) {
            case MainMenu:
                return sMainMenuScreen;
            case Credits:
                return sCreditScreen;
            case NewGame:
                return sNewGameScreen;
            case GameOver:
                return sGameOverScreen;
            case LoadGame:
                return sLoadGameScreen;
            case MainGame:
                return sMainGameScreen;
            case WatchIntro:
                return sCutSceneScreen;
            default:
                return sMainMenuScreen;
        }
    }

    @Override
    public void create() {
        sMainGameScreen = new MainGameScreen(this);
        sCutSceneScreen = new CutSceneScreen(this);
        sGameOverScreen = new GameOverScreen(this);
        sMainMenuScreen = new MainGameScreen(this);
        sLoadGameScreen = new LoadGameScreen(this);
        sNewGameScreen = new NewGameScreen(this);
        sCreditScreen = new CreditScreen(this);
    }

    @Override
    public void dispose() {
        sMainGameScreen.dispose();
        sMainMenuScreen.dispose();
        sGameOverScreen.dispose();
        sCutSceneScreen.dispose();
        sLoadGameScreen.dispose();
        sNewGameScreen.dispose();
        sCreditScreen.dispose();
    }
}

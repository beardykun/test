package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.audio.AudioSubject;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, AudioSubject {

    private Array<AudioObserver> observers;

    public GameScreen(){
        observers = new Array<AudioObserver>();
        this.addObserver(AudioManager.getInstance());
    }

    @Override
    public void addObserver(AudioObserver audioObserver) {
        observers.add(audioObserver);
    }

    @Override
    public void removeObserver(AudioObserver audioObserver) {
        observers.removeValue(audioObserver, true);
    }

    @Override
    public void removeAllObservers() {
        observers.removeAll(observers, true);
    }

    @Override
    public void notify(AudioObserver.AudioCommand command, AudioObserver.AudioTypeEvent event) {
        for (AudioObserver observer: observers){
            observer.onNotify(command, event);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

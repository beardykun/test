package com.mygdx.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Utility;

import java.util.Hashtable;

public class AudioManager implements AudioObserver {
    private static final String TAG = AudioManager.class.getSimpleName();

    private static AudioManager instance = null;

    private Hashtable<String, Music> queuedMusic;
    private Hashtable<String, Sound> queuedSound;

    private AudioManager() {
        queuedMusic = new Hashtable<String, Music>();
        queuedSound = new Hashtable<String, Sound>();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    @Override
    public void onNotify(AudioCommand command, AudioTypeEvent event) {
        switch (command) {
            case MUSIC_LOAD:
                Utility.loadMusicAsset(event.getValue());
                break;
            case MUSIC_PLAY_ONCE:
                playMusic(false, event.getValue());
                break;
            case MUSIC_PLAY_LOOP:
                playMusic(true, event.getValue());
                break;
            case MUSIC_STOP:
                Music music = queuedMusic.get(event.getValue());
                if (music != null) {
                    music.stop();
                }
                break;
            case MUSIC_STOP_ALL:
                for (Music musicStop : queuedMusic.values()) {
                    musicStop.stop();
                }
                break;
            case SOUND_LOAD:
                Utility.loadSoundAsset(event.getValue());
                break;
            case SOUND_PLAY_ONCE:
                playSound(false, event.getValue());
                break;
            case SOUND_PLAY_LOOP:
                playSound(true, event.getValue());
                break;
            case SOUND_STOP:
                Sound sound = queuedSound.get(event.getValue());
                if (sound != null) {
                    sound.stop();
                }
                break;
            default:
                break;
        }
    }

    public Music playMusic(boolean isLooping, String fullFilePath) {
        Music music = queuedMusic.get(fullFilePath);
        if (music != null) {
            music.setLooping(isLooping);
            music.play();
        } else if (Utility.isAssetLoaded(fullFilePath)) {
            music = Utility.getMusicAsset(fullFilePath);
            music.setLooping(isLooping);
            music.play();
            queuedMusic.put(fullFilePath, music);
        } else {
            Gdx.app.debug(TAG, "Music not loaded");
            return null;
        }
        return music;
    }

    public Sound playSound(boolean isLooping, String fullFilePath) {
        Sound sound = queuedSound.get(fullFilePath);
        if (sound != null) {
            long soundId = sound.play();
            sound.setLooping(soundId, isLooping);
            sound.play();
        } else if (Utility.isAssetLoaded(fullFilePath)) {
            sound = Utility.getSounAsset(fullFilePath);
            long soundId = sound.play();
            sound.setLooping(soundId, isLooping);
            queuedSound.put(fullFilePath, sound);
        } else {
            Gdx.app.debug(TAG, "Sound not loaded");
            return null;
        }
        return sound;
    }

    public void dispose() {
        for (Music music : queuedMusic.values()) {
            music.dispose();
        }
        for (Sound sound : queuedSound.values()) {
            sound.dispose();
        }
    }
}

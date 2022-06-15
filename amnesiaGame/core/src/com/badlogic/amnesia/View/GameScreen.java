package com.badlogic.amnesia.View;

import com.badlogic.amnesia.AmnesiaGame;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    private final AmnesiaGame game;

    public GameScreen (final AmnesiaGame game) {
        this.game = game;
    }
    
    @Override
    public void render(float delta) {

    }
    
    @Override
    public void dispose() {

    }

    public void resize(int width, int height){
    }

    public void pause() {
    }

    public void resume() {
    }

    public void show() {
    }

    public void hide() {
    }
}

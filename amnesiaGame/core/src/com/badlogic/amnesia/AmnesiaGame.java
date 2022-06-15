package com.badlogic.amnesia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.amnesia.View.MainMenu;

public class AmnesiaGame extends Game {
    
    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // using libGDX default font: Arial
		this.setScreen(new MainMenu(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
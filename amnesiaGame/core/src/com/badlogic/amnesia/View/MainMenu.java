package com.badlogic.amnesia.View;

import com.badlogic.amnesia.AmnesiaGame;
import com.badlogic.amnesia.Services.FileManager.FileController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu implements Screen {
    //------------------------------------------------------------------------------------
    private final AmnesiaGame game;
    
    private Texture backgroundImage;
    private Sprite menuBackground;
    private SpriteBatch spriteBatch;
    
    private OrthographicCamera orthographicCamera;
    
    private Vector3 touchPosition;
    private Rectangle newGameButton;
    private Rectangle configButton;

    private FileController fc = new FileController();
    private String gameConfigFile;
    //------------------------------------------------------------------------------------
    public MainMenu (final AmnesiaGame game) {
        this.game = game;

        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, 700, 500);

        newGameButton = new Rectangle();
        newGameButton.x = 212;
        newGameButton.y = 256;
        newGameButton.width = 271;
        newGameButton.height = 33;

        configButton = new Rectangle();
        configButton.x = 158;
        configButton.y = 339;
        configButton.width = 401;
        configButton.height = 34;

        this.gameConfigFile = "SaveFile.csv";
    }
    //------------------------------------------------------------------------------------
    private void renderMenuBackground() {
        backgroundImage = new Texture(Gdx.files.internal("menuBackground.png"));
        menuBackground = new Sprite(backgroundImage);
        game.batch.draw(menuBackground, 0, 0);
    }
    //------------------------------------------------------------------------------------
    private void showBindConfigScreen() {
        // insert new screen here
    }
    //------------------------------------------------------------------------------------
    private void confirmGameModeAndLoadGame() {
        boolean hasResetSaveFile = false;
        
        try {
            hasResetSaveFile = !fc.isFileEmpty("ResetSaveFile.csv");
        } catch (Exception e) {
            System.out.println("Cannot found or read SaveFile.csv: " + e.getMessage());
        }
        
        if (hasResetSaveFile) {
            Stage stage = new Stage();
            Skin skin = new Skin();
            TextButton confirmButton = new TextButton("Sim", skin),
                        cancelButton = new TextButton("Não", skin);

            Dialog dialogGameMode = new Dialog("Modo de Jogo", skin, "dialog") {
                public void result(Object obj) {
                    if (obj.equals("confirmButton")) {
                        this.gameConfigFile = "ResetSaveFile.csv";
                    }
                }
            };
            dialogGameMode.text("Deseja recuperar as configurações de seu último jogo?");
            dialogGameMode.button(confirmButton, "confirmButton");
            dialogGameMode.button(cancelButton, "cancelButton");
            dialogGameMode.show(stage);
        }

        this.loadGame(this.gameConfigFile);
    }
    //------------------------------------------------------------------------------------
    private void loadGame(String gameConfigFile) {
        game.setScreen(new GameScreen(game));
        this.dispose();
    }
    //------------------------------------------------------------------------------------
    // interface Screen methods
    @Override
    public void render(float delta) {
        orthographicCamera.update();
        game.batch.setProjectionMatrix(orthographicCamera.combined);

        game.batch.begin();
        renderMenuBackground();
        game.batch.end();

        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            orthographicCamera.unproject(touchPosition);
            
            if (newGameButton.contains(touchPosition.x, touchPosition.y)) {
                this.confirmGameModeAndLoadGame();
            }
            
            if (configButton.contains(touchPosition.x, touchPosition.y)) {
                this.showBindConfigScreen();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        spriteBatch.dispose();
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

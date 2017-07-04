package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Laurenz on 03.07.2017.
 */

public class GameOver implements Screen {

    private Label label;
    private TextureRegion table_bg;
    private TextureAtlas textureAtlas;
    private Table table;
    private BitmapFont title;
    Controller controller;
    Model model;
    Stage stage;
    OrthographicCamera camera;
    private KeyBack_Menu_Screen game;

    private TextButton back_button;
    private Texture texture;
    private SpriteBatch batch;


    public GameOver(KeyBack_Menu_Screen g) {

        game = g;
        camera = new OrthographicCamera();

        batch = new SpriteBatch();
        stage = new Stage();


        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); //Skins für Actor

        camera = new OrthographicCamera();      //Setzt Kamera
        camera.setToOrtho(false, 1920, 1080); //Kameragröße
        camera.update();


        //All Events will be Handeld by Controller Class
        Gdx.input.setInputProcessor(stage);

        //Schriftgröße wird geändert
        skin.getFont("default-font").getData().setScale(3, 3);

        //Hintergrundbild
        texture = new Texture(Gdx.files.internal("menu_screen.jpg"));

        //Tabelle
        table = new Table();
        table.defaults().height(stage.getHeight() / 2);
        table.defaults().width(stage.getWidth() / 2);
        table.setFillParent(true);

        label = new Label("GAME OVER", skin);
        table.add(label).pad(15);
        table.row();


        //table_bg.setRegionWidth((int) table.getWidth()/2);
        //table_bg.setRegionHeight((int) table.getHeight()/2);

        //table.setBackground(new TextureRegionDrawable(new TextureRegion(table_bg)));

        //Wird der Stage hinzugefügt (Layout - Behälter)
        stage.addActor(table);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 2, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();

        //Catch Back Button
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new Menu_Screen(game));
        }
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

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Bernhard on 28.06.2017.
 */

class Highscore_Screen implements Screen {

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


    public Highscore_Screen(KeyBack_Menu_Screen g) {

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

        //Button
        back_button = new TextButton("Back", skin);
        back_button.setSize(back_button.getWidth() + 100, back_button.getHeight() + 100);
        back_button.setPosition(Gdx.graphics.getWidth() - 100 - back_button.getWidth() / 2, Gdx.graphics.getHeight() - 100 - back_button.getHeight() / 2); //Oben Rechts von Bildschirm


        //Hintergrundbild
        texture = new Texture(Gdx.files.internal("menu_screen.jpg"));

        //Tabelle
        table = new Table();
        table.defaults().height(stage.getHeight()/2);
        table.defaults().width(stage.getWidth()/2);
        table.setFillParent(true);

        label = new Label("Highscore:", skin);
        table.add(label).pad(15);
        table.row();

        textureAtlas = new TextureAtlas("highscore_bg.txt");
        table_bg = textureAtlas.findRegion("highscore_bg");
        table_bg.setRegionWidth((int) table.getWidth());
        table_bg.setRegionHeight((int) table.getHeight());

        table.setBackground(new TextureRegionDrawable(new TextureRegion(table_bg)));


        //Actors werden Listener hinzugefügt
        back_button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                // zurück zum Menu_Screen
            }
        });

        //Wird der Stage hinzugefügt (Layout - Behälter)
        stage.addActor(table);
        stage.addActor(back_button);


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

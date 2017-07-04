package com.mygdx.game;

/**
 * Created by Laurenz on 15.11.2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


/**
 * Created by Laurenz on 15.11.2015.
 */
public class Menu_Screen implements Screen {

    Controller controller;
    Model model;
    Stage stage;
    OrthographicCamera camera;
    private KeyBack_Menu_Screen game;

    private TextButton menu_screen_button;
    //private TextButton menu_screen_highscore_button;
    private Texture texture;
    private SpriteBatch batch;

    /**
     * Konstruktor, gleicht der create- Methode
     *
     * @param g
     */
    public Menu_Screen(KeyBack_Menu_Screen g) {
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

        //Tabelle und Button wird erstellt und Positioniert
//        Table table = new Table();
        menu_screen_button = new TextButton("Start Game", skin);
       // menu_screen_highscore_button = new TextButton("Highscore", skin);
        menu_screen_button.setSize(menu_screen_button.getWidth() + 300, menu_screen_button.getHeight() + 100);
       // menu_screen_highscore_button.setSize(menu_screen_highscore_button.getWidth() + 100, menu_screen_highscore_button.getHeight() + 100);
        menu_screen_button.setPosition(Gdx.graphics.getWidth() / 2 - menu_screen_button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu_screen_button.getHeight() / 2); //Mitte von Bildschirm
       // menu_screen_highscore_button.setPosition(Gdx.graphics.getWidth() - 100 - menu_screen_button.getWidth() / 2, Gdx.graphics.getHeight() - 100 - menu_screen_button.getHeight() / 2); //Oben Rechts von Bildschirm


        //Hintergrundbild
        texture = new Texture(Gdx.files.internal("menu_screen.jpg"));

        //Actors werden Listener hinzugefügt
        menu_screen_button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Spiel_Screen(game));

            }
        });


        /*
        menu_screen_highscore_button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                //Show Highscore Screen
                game.setScreen(new Highscore_Screen(game));
                System.out.println(actor.getName());
            }
        });
*/


        //Wird der Stage hinzugefügt (Layout - Behälter)
        stage.addActor(menu_screen_button);
     //   stage.addActor(menu_screen_highscore_button);


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

    public TextButton getMenu_screen_button() {
        return menu_screen_button;
    }

    public void setMenu_screen_button(TextButton menu_screen_button) {
        this.menu_screen_button = menu_screen_button;
    }
}

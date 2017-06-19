package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class KeyBack_Menu_Screen extends Game {

    SpriteBatch batch; //Wenn ein Actor gezeichnet werden soll

    @Override
    /**
     * Lädt alle relevanten Datein (Konstruktor)
     */
    public void create() {
        this.setScreen(new Menu_Screen(this));
        batch = new SpriteBatch();
    }

    @Override
    /**
     * Rendert alle Datein auf den Screen
     */
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(); //Wichtig

        batch.begin();    //Hier müssen die Actors drinnen stehen
        batch.end();
    }

    /**
     * Löscht nichtgebrauchte Objekte
     */
    public void dispose() {
        batch.dispose();

    }
}

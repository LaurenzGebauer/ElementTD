package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Created by Laurenz on 01.12.2015.
 */
public class Controller extends DragListener  {

    Spiel_Screen spielScreen;
    Model model;

    /**
     * @param spielScreen
     * @param model
     */
    public Controller(Spiel_Screen spielScreen, Model model) {
        this.spielScreen = spielScreen;
        this.model = model;

    }

    /**
     * Methoden die sich aufruft wenn ein Objekt gezogen wird
     * @param
     * @param
     */
    public void drag(InputEvent event, float x, float y, int pointer) {

        spielScreen.getCamera().unproject(spielScreen.getTouchPoint().set(Gdx.input.getX(), Gdx.input.getY(), 0));
        spielScreen.getSourceImage().moveBy(x - spielScreen.getSourceImage().getWidth() / 2, y - spielScreen.getSourceImage().getHeight() / 2); //erspart sich das setzen von final

    }

    /**
     *Methode die verwendetet wird wenn das gezogene Objekt zum Stillstand kommt
     * @param
     * @param
     */
    public void dragStop(InputEvent event, float x, float y, int pointer) {
//        if (model.pointInRectangles(spielScreen.getRects(), spielScreen.getTouchPoint().x, spielScreen.getTouchPoint().y)) {
//            spielScreen.getSourceImage().remove();
//        }
//        else spielScreen.getSourceImage().clearListeners();
    }
}

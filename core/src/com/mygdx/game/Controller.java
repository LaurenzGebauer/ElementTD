package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.kotcrab.vis.ui.widget.Menu;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Laurenz on 01.12.2015.
 */
public class Controller extends DragListener implements ChangeListener {

    Spiel_Screen spielScreen;
    Menu_Screen menuScreen;
    Model model;
    KeyBack_Menu_Screen game;

    /**
     * @param spielScreen
     * @param model
     */
    public Controller(Spiel_Screen spielScreen, Model model, KeyBack_Menu_Screen game) {
        this.spielScreen = spielScreen;
        this.model = model;
        this.game = game;
   }


    /**
     * Methoden die sich aufruft wenn ein Objekt gezogen wird
     * @param
     * @param
     */
    public void drag(InputEvent event, float x, float y, int pointer) {

//        spielScreen.getCamera().unproject(spielScreen.getTouchPoint().set(Gdx.input.getX(), Gdx.input.getY(), 0));
//        spielScreen.getSourceImage().moveBy(x - spielScreen.getSourceImage().getWidth() / 2, y - spielScreen.getSourceImage().getHeight() / 2); //erspart sich das setzen von final

    }

    /**
     *Methode die verwendetet wird wenn das gezogene Objekt zum Stillstand kommt
     * @param
     * @param
     */
    public void dragStop(InputEvent event, float x, float y, int pointer) {
//        if (model.pointInRectangles(spielScreen.getTiled_tower_fields(), spielScreen.getTouchPoint().x, spielScreen.getTouchPoint().y)) {
//            spielScreen.getSourceImage().remove();
//        }
//        else spielScreen.getSourceImage().clearListeners();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        System.out.println("Hi");
    }

//    @Override
//    public void stateChanged(ChangeEvent e) {
//        System.out.println("Jude");
//    }
}

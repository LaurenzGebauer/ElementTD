package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Created by Laurenz on 01.12.2015.
 */
public class Controller extends DragListener  {

    Start2 start2;
    Model model;


    public Controller(Start2 start2,Model model){
        this.start2 =  start2;
        this.model = model;

    }

    public void drag(InputEvent event, float x, float y, int pointer) {

        start2.getCamera().unproject(start2.getTouchPoint().set(Gdx.input.getX(), Gdx.input.getY(), 0));
        start2.getSourceImage().moveBy(x - start2.getSourceImage().getWidth() / 2, y - start2.getSourceImage().getHeight() / 2); //erspart sich das setzen von final

    }

    public void dragStop(InputEvent event, float x, float y, int pointer) {
//        if (model.pointInRectangles(start2.getRects(), start2.getTouchPoint().x, start2.getTouchPoint().y)) {
//            start2.getSourceImage().remove();
//        }
//        else start2.getSourceImage().clearListeners();
    }
}

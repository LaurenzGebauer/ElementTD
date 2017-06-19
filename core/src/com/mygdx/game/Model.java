package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Laurenz on 01.12.2015.
 */
public class Model {

    Spiel_Screen start;


    public Model(Spiel_Screen start) {
        this.start= start;
    }

    /**
     * Return die Rectangle Map-Objekte
     * @param tiledMap
     * @param starts
     * @param tiledobject
     * @return
     */
    public Array<Rectangle> getTiledObjects(TiledMap tiledMap, Array<Rectangle> starts, String tiledobject){
        for (MapObject object : tiledMap.getLayers().get(tiledobject).getObjects()) {
            if (object instanceof RectangleMapObject) {
                starts.add(((RectangleMapObject) object).getRectangle());
            }
        }
        return starts;
    }
    /**
     *
     * Ließt  die Aktuellen Positionen der Objekte im Parameter aus und überprüft ob sie bei einer bestimmten Position matchen
     * @param label
     * @param rec
     */
    public void checkpos(MoveToAction ac , Label label, Array<Rectangle> rec) {
        if ((int) label.getX() == (int) rec.get(1).x && (int) label.getY() == (int) rec.get(1).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(2).x, rec.get(2).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(2).x && (int) label.getY() == (int) rec.get(2).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(3).x, rec.get(3).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(3).x && (int) label.getY() == (int) rec.get(3).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(4).x, rec.get(4).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(4).x && (int) label.getY() == (int) rec.get(4).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(5).x, rec.get(5).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(4).x && (int) label.getY() == (int) rec.get(4).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(5).x, rec.get(5).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(5).x && (int) label.getY() == (int) rec.get(5).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(6).x, rec.get(6).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(6).x && (int) label.getY() == (int) rec.get(6).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(7).x, rec.get(7).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(7).x && (int) label.getY() == (int) rec.get(7).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(8).x, rec.get(8).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(8).x && (int) label.getY() == (int) rec.get(8).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(9).x, rec.get(9).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(9).x && (int) label.getY() == (int) rec.get(9).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(10).x, rec.get(10).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(10).x && (int) label.getY() == (int) rec.get(10).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(11).x, rec.get(11).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
        if ((int) label.getX() == (int) rec.get(11).x && (int) label.getY() == (int) rec.get(11).y) {
            label.removeAction(ac);
            ac = new MoveToAction();
            ac.setDuration(3);
            ac.setPosition(rec.get(12).x, rec.get(12).y);
            label.addAction(ac);
            //label.addAction(Actions.moveTo(starts.get(2).x, starts.get(2).y, 3));
        }
    }

    /**
     * Überprüft ob die Kooardinaten mit den Rectangle matchen
     * @param r
     * @param x
     * @param y
     * @return
     */
    public static boolean pointInRectangles(Array<Rectangle> r, float x, float y) {
        for (int i = 0; i < r.size; i++) {
            if (r.get(i).x <= x && r.get(i).x + r.get(i).width >= x && r.get(i).y <= y && r.get(i).y + r.get(i).height >= y)
                return true;
        }
        return false;
    }



}

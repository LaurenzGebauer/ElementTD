package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Laurenz on 01.12.2015.
 */
public class Model {

    Spiel_Screen start;
    ArrowTower arrowTower;

    private int mMode = UNDEFINED;

    public static final int UNDEFINED = 0;
    public static final int DRAW_OPEN_FIELDS = 1;
    public String setTowerNumberClicked = null;
    public String towerNameClicked ="";
    private Sprite sprite;
    private Texture texture;


    public Model(Spiel_Screen start) {
        this.start= start;
    }

    /**
     * Ließt von Tiled Map die Rectangle aus und erstellt davon Rectangle
     * Return die Rectangle Map-Objekte
     * @param tiledMap Karte
     * @param starts   Rectangle Array mit enthalten Tiles
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
     * NPC's Laufen Route ab
     * @param label
     * @param rec
     */
    public void npc_route_running(MoveToAction ac , Label label, Array<Rectangle> rec) {
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
     * Zeichnet Grüne Felder auf Felder wenn sie Frei sind
     * @param spiel_screen
     * @param sr
     * @param spriteBatch
     * @param r
     */
    public void drawEmptyFields(Spiel_Screen spiel_screen, ShapeRenderer sr, SpriteBatch spriteBatch, Array<Rectangle> r){
        for (int i = 0; i < r.size; i++) {
            //Freie Flächen Grün
            sr.setProjectionMatrix(spriteBatch.getProjectionMatrix());
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.GREEN);
            sr.rect(r.get(i).x-110,r.get(i).y, r.get(i).width,r.get(i).height);
            sr.end();

        }
    }

    public void notEmptyFields(){

     }

    /**
     * Zeichnet den Tower an der Stelle
     * @param spiel_screen
     * @param r
     * @param i
     */
    public void drawTower(Spiel_Screen spiel_screen, Array<Rectangle> r , int i){

                spiel_screen.getSpriteBatch().draw(spiel_screen.getUiskin().getRegion(towerNameClicked),r.get(i).x-110 ,r.get(i).y);
    }

    /**
     * Nimmt die Tower ID und wandelt es in den passendenden Tower Namen um
     * @param TowerNumber
     * @return
     */
    public String changeTowerNumbertoName(String TowerNumber){
        int num = Integer.parseInt(TowerNumber);

        if(num==0){
            towerNameClicked="arrow";
            return "arrow";
        }
        else
        if(num==1){
            towerNameClicked= "cannon";
        }
        else
        if(num==2){
            towerNameClicked="fire";
        }
        else
        if(num==3){
            towerNameClicked= "water";
        }
        else
        if(num==4){

            towerNameClicked= "darkness";
        }
        else
        if(num==5){
            towerNameClicked= "light";
        }
        else
        if(num==6){
            towerNameClicked= "nature";
        }
        else
        if(num==7){
            towerNameClicked= "earth";
        }
         return towerNameClicked;

      }

    public int getMode() {
        return mMode;
    }


    public void setMode(int _mode) { mMode = _mode; }

}

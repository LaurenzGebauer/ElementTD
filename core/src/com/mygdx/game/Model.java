package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    Tower tower;

    private int mMode = UNDEFINED;

    public static final int UNDEFINED = 0;
    public static final int DRAW_OPEN_FIELDS = 1;

    Sprite sprittower;

    protected boolean allEnemysDead = false;



    public int getTowerplacementobserver() {
        return towerplacementobserver;
    }

    public void setTowerplacementobserver(int towerplacementobserver) {
        this.towerplacementobserver = towerplacementobserver;
    }

    private int towerplacementobserver = UNDEFINED;
    public static final int towervalid = 0;
    public static final int towerinvalid = 1;

    public String setTowerNumberClicked = null;
    public String towerNameClicked ="arrow";

    private Sprite sprite;
    private Texture texture;
    public int towercost;

    public Model(Spiel_Screen start) {
        this.start= start;
        this.sprite = new Sprite();

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
     * NPC's laufen Route ab
     * @param enemy
     * @param rec
     */
    public void npc_route_running(MoveToAction ac , Enemy enemy, Array<Rectangle> rec) {
        for(int i = 1; i < rec.size; i++){
            if ((int) enemy.getX() == (int) rec.get(i).getX() && (int) enemy.getY() == (int) rec.get(i).getY() && enemy.isAlive) {
                enemy.removeAction(ac);
                ac = new MoveToAction();
                if(i<rec.size-1){
                    enemy.setDir(checkDirection(rec.get(i), rec.get(i+1)));
                    ac.setPosition(rec.get(i+1).x, rec.get(i+1).y);
                    float time = calcDurationOfMovement(enemy.getX(), enemy.getY(), rec.get(i+1).x, rec.get(i+1).y);
                    ac.setDuration(time);
                    enemy.addAction(ac);
                }
            }
            if (!enemy.isAlive) {
                enemy.clear();
            }

        }
    }

    public static Enemy.Dir checkDirection(Rectangle current, Rectangle next){
        if(current.getX() > next.getX()) {
            return Enemy.Dir.LEFT;
        } else if (current.getX() < next.getX()) {
            return Enemy.Dir.RIGHT;
        } else if (current.getY() > next.getY()) {
            return Enemy.Dir.DOWN;
        } else if (current.getY() < next.getY()){
            return Enemy.Dir.UP;
        }
        return null;
    }

    public static float calcDurationOfMovement(float x, float y, float nextX, float nextY) {
        float distanceX = Math.abs(x - nextX);
        float distanceY = Math.abs(y - nextY);
        float distancePerSecond = 180.0f;
        float time = (distanceX + distanceY) / distancePerSecond;
        return time;
    }

    public boolean checkIfAllEnemysDead (Array<Enemy> enemys) {
        for (int i = 0; i < enemys.size; i++) {
            if (enemys.get(i).isVisible()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Zeichnet Grüne Felder auf Felder wenn sie Frei sind
     * @param spiel_screen
     * @param ta
     * @param spriteBatch
     * @param r
     */

    public void drawEmptyFields(Spiel_Screen spiel_screen, TextureAtlas ta, SpriteBatch spriteBatch, Array<Rectangle> r){
       ta=new TextureAtlas("freeTiles.txt");
       Texture freeTilesTexture = ta.findRegion("freeTiles").getTexture();

        for (int i = 0; i < r.size; i++) {
           spriteBatch.draw(freeTilesTexture, r.get(i).x,r.get(i).y, r.get(i).width,r.get(i).height);
        }
    }

    public void drawSprite(String name, float x, float y) {

        Sprite sprite = start.getTowermenuicons().createSprite(name);
        sprite.setPosition(x, y);
        sprite.draw(start.getSpriteBatch());
    }

    public Sprite getTowerSprite(String name) {
        return start.getTowermenuicons().createSprite(name);
    }

    public void spirteexample(Sprite sprite) {

    }

    public void notEmptyFields(){

    }



    /**
     * Zeichnet den Tower an der Stelle
     * @param
     * @param r
     * @param i
     */
    public void drawTower(Array<Rectangle> r , int i){
        start.getSpriteBatch().draw(textureRegionconverter(),r.get(i).x ,r.get(i).y);
    }

    public Rectangle towerRange(float x, float y, float width, float height){
        Rectangle towerRange = new Rectangle();
        towerRange.set(x-width/2,y-height/2,width,height);
        return towerRange;
    }

    public TextureRegion textureRegionconverter(){

        return start.getUiskin().getRegion(towerNameClicked);
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
            towercost=10;
            return "arrow";
        }
        else if(num==1){
            towerNameClicked= "cannon";
            towercost=15;
        }
        else if(num==2){
            towerNameClicked="fire";
            towercost=50;
        }
        else if(num==3){
            towerNameClicked= "water";
            towercost=50;
        }
        else if(num==4){
            towerNameClicked= "darkness";
            towercost=50;
        }
        else if(num==5){
            towerNameClicked= "light";
            towercost=50;
        }
        else if(num==6){
            towerNameClicked= "nature";
            towercost=50;
        }
        else if(num==7){
            towerNameClicked= "earth";
            towercost=50;
        }
        return towerNameClicked;
    }

    public Tower.TowerType getTowerTypeFromName (String towerName) {
        Tower.TowerType type = Tower.TowerType.valueOf(towerName.toUpperCase());
        return type;
    }

    public int getTowerCost () {
        Tower.TowerType type = this.getTowerTypeFromName(towerNameClicked);
        Tower tower = Tower.createTower(type, new Sprite(), 0, 0, new Rectangle());
        return tower.getCost();
    }

    public int getMode() {
        return mMode;
    }
    public void setMode(int _mode) {
        mMode = _mode;
    }
}

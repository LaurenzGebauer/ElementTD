package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Laurenz on 28.06.2017.
 */

public class Tower {

    protected TowerType type;
    private int damage;
    private float fireDelay;
    private int cost;
    protected Rectangle range;
    private Sprite sprite;
    private float positionx;
    private float positiony;

    protected enum TowerType {
        ARROW, CANNON, FIRE, WATER, DARKNESS, LIGHT, NATURE, EARTH
    }

    public Tower(TowerType type, int damage, float fireDelay, int cost, Sprite sprite, float x, float y, Rectangle range) {
        this.type = type;
        this.damage = damage;
        this.fireDelay = fireDelay;
        this.cost = cost;
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
        this.positionx=x;
        this.positiony=y;
        this.range = new Rectangle(range);
    }

    public static Tower createTower(TowerType type, Sprite sprite, float x, float y, Rectangle range){
        int damage;
        float fireDelay;
        int cost;

        switch (type) {
            case ARROW:
                damage = 2;
                fireDelay = 1.0f;
                cost = 10;
                break;
            case CANNON:
                damage = 3;
                fireDelay = 1.4f;
                cost = 15;
                break;
            case FIRE:
                damage = 5;
                fireDelay = 2.0f;
                cost = 30;
                break;
            case WATER:
                damage = 7;
                fireDelay = 2.3f;
                cost = 35;
                break;
            case DARKNESS:
                damage = 10;
                fireDelay = 2.7f;
                cost = 40;
                break;
            case LIGHT:
                damage = 13;
                fireDelay = 3.1f;
                cost = 45;
                break;
            case NATURE:
                damage = 16;
                fireDelay = 3.5f;
                cost = 50;
                break;
            case EARTH:
                damage = 20;
                fireDelay = 4.0f;
                cost = 55;
                break;
            default:
                damage = 1;
                fireDelay = 1.0f;
                cost = 1;
                break;
        }
        return new Tower(type, damage, fireDelay, cost, sprite, x, y, range);
    }

     public void draw (Batch batch,float x,float y) {
         this.sprite.setPosition(x,y);
         this.sprite.draw(batch);
    }

    public void upgrade() {
        this.damage += 10;
        //this.range += 10;
    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public float getFireDelay() {
        return fireDelay;
    }
    public void setFireDelay(float fireDelay) {
        this.fireDelay = fireDelay;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public Rectangle getRange() {
        return range;
    }
    public void setRange(Rectangle range) {
        this.range = range;
    }
    public float getPositionx() {
        return positionx;
    }
    public void setPositionx(float positionx) {
        this.positionx = positionx;
    }
    public float getPositiony() {
        return positiony;
    }
    public void setPositiony(float positiony) {
        this.positiony = positiony;
    }
}



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

public class ArrowTower {

    private int damage = 5;
    protected Rectangle range;
    private Sprite sprite;
    private float positionx;
    private float positiony;


    public ArrowTower (Sprite sprite, float x, float y, Rectangle range) {
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
        this.positionx=x;
        this.positiony=y;
        this.range = new Rectangle(range);
    }

     public void draw (Batch batch,float x,float y) {
         this.sprite.setPosition(x,y);
        sprite.draw(batch);
    }

    public void upgrade() {
        damage=+10;
        // range=+10;
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



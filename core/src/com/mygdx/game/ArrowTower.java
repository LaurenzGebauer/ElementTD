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

    Texture region;
    TextureRegion tregion;

    private int damage = 5;
    protected Rectangle range;

    private Sprite sprite;

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

    public ArrowTower () {

        region = new Texture(Gdx.files.internal("Hero.png"));
        tregion= new TextureRegion(region);
        this.sprite = new Sprite(region);
    }

    public ArrowTower (Sprite sprite, float x, float y, Rectangle range) {
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
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

}



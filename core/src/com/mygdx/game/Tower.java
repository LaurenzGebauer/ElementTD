package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Laurenz on 28.06.2017.
 */

public class Tower {

    protected TowerType type;
    private int damage;
    private float nextShotDelay;
    protected float fireDelay;
    private int cost;
    protected Rectangle range;
    private Sprite sprite;
    protected ParticleEffect particleEffect;
    protected boolean showParticles;
    private float positionx;
    private float positiony;

    protected enum TowerType {
        ARROW, CANNON, FIRE, WATER, DARKNESS, LIGHT, NATURE, EARTH
    }

    public Tower(TowerType type, int damage, float nextShotDelay, int cost, Sprite sprite, ParticleEffect particleEffect, float x, float y, Rectangle range) {
        this.type = type;
        this.damage = damage;
        this.nextShotDelay = nextShotDelay;
        this.fireDelay = 0f;
        this.cost = cost;
        this.sprite = sprite;
        this.sprite.setPosition(x, y);
        this.positionx=x;
        this.positiony=y;
        this.particleEffect = particleEffect;
        this.particleEffect.setPosition(this.getPositionx()+50, this.getPositiony()+20);
        this.particleEffect.scaleEffect(0.7f);
        this.particleEffect.allowCompletion();
        this.showParticles = false;
        this.range = new Rectangle(range);
    }

    public static Tower createTower(TowerType type, Sprite sprite, float x, float y, Rectangle range){
        int damage;
        float nextShotDelay;
        int cost;
        ParticleEffect effect = new ParticleEffect();

        switch (type) {
            case ARROW:
                damage = 3;
                nextShotDelay = 1.0f;
                cost = 10;
                effect.load(Gdx.files.internal("particle/arrow_particles"), Gdx.files.internal("particle"));
                break;
            case CANNON:
                damage = 5;
                nextShotDelay = 1.3f;
                cost = 15;
                effect.load(Gdx.files.internal("particle/cannon_particles"), Gdx.files.internal("particle"));
                break;
            case FIRE:
                damage = 6;
                nextShotDelay = 2.0f;
                cost = 30;
                effect.load(Gdx.files.internal("particle/fire_particles"), Gdx.files.internal("particle"));
                break;
            case WATER:
                damage = 8;
                nextShotDelay = 2.3f;
                cost = 35;
                effect.load(Gdx.files.internal("particle/water_particles"), Gdx.files.internal("particle"));
                break;
            case DARKNESS:
                damage = 10;
                nextShotDelay = 2.7f;
                cost = 40;
                effect.load(Gdx.files.internal("particle/darkness_particles"), Gdx.files.internal("particle"));
                break;
            case LIGHT:
                damage = 13;
                nextShotDelay = 3.1f;
                cost = 45;
                effect.load(Gdx.files.internal("particle/light_particles"), Gdx.files.internal("particle"));
                break;
            case NATURE:
                damage = 16;
                nextShotDelay = 3.5f;
                cost = 50;
                effect.load(Gdx.files.internal("particle/nature_particles"), Gdx.files.internal("particle"));
                break;
            case EARTH:
                damage = 20;
                nextShotDelay = 4.0f;
                cost = 55;
                effect.load(Gdx.files.internal("particle/earth_particles"), Gdx.files.internal("particle"));
                break;
            default:
                damage = 1;
                nextShotDelay = 1.0f;
                cost = 1;
                effect.load(Gdx.files.internal("particle/test"), Gdx.files.internal("particle"));
                break;
        }
        return new Tower(type, damage, nextShotDelay, cost, sprite, effect, x, y, range);
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
    public float getNextShotDelay() {
        return nextShotDelay;
    }
    public void setNextShotDelay(float nextShotDelay) {
        this.nextShotDelay = nextShotDelay;
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



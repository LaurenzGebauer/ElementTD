package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by markus on 01.07.17.
 */

public class Enemy extends Actor {

    protected Array<Animation> animations;
    protected Animation animatedNpc;
    //protected TextureRegion currentFrame;
    private int health;
    private int goldReward;
    protected Dir dir;


    public Enemy(Array<Animation> animations, int health, int goldReward) {
        this.animations = animations;
        this.animatedNpc = animations.get(2);
        this.health = health;
        this.goldReward = goldReward;
        this.dir = Dir.DOWN;
    }

    protected enum Dir {
        LEFT, RIGHT, UP, DOWN
    }

    public Animation getAnimatedNpc() {
        return animatedNpc;
    }
    public void setAnimatedNpc(Animation animatedNpc) {
        this.animatedNpc = animatedNpc;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getGoldReward() {
        return goldReward;
    }
    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    /*public TextureRegion getCurrentFrame() {
        return currentFrame;
    }
    public void setCurrentFrame(TextureRegion currentFrame) {
        if (this.dir == Dir.LEFT) {
            currentFrame.flip(true, false);
        }
        this.currentFrame = currentFrame;
    }*/

    public Dir getDir() {
        return dir;
    }
    public void setDir(Dir dir) {
        this.dir = dir;
        this.chooseAnimationByDir();
    }

    public static Enemy createEnemy(Array<Animation> animations, int health, int goldReward) {
        Enemy enemy = new Enemy(animations, health, goldReward);
        return enemy;
    }

    public void chooseAnimationByDir() {
        if (this.dir == Dir.LEFT) {
            this.animatedNpc = this.animations.get(1);
        } else if (this.dir == Dir.RIGHT) {
            this.animatedNpc = this.animations.get(3);
        } else if (this.dir == Dir.UP) {
            this.animatedNpc = this.animations.get(0);
        } else if (this.dir == Dir.DOWN) {
            this.animatedNpc = this.animations.get(2);
        }
    }

}

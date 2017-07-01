package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by markus on 01.07.17.
 */

public class Enemy extends Actor {

    protected Animation animatedNpc;
    protected boolean flipY;
    private int health;
    private int goldReward;
    protected Dir dir;



    public Enemy(Animation animation, int health, int goldReward) {
        this.animatedNpc = animation;
        this.health = health;
        this.goldReward = goldReward;
        this.dir = Dir.DOWN;
    }

    protected enum Dir {
        LEFT, RIGHT, UP, DOWN
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

    public Dir getDir() {
        return dir;
    }
    public void setDir(Dir dir) {
        this.dir = dir;
        System.out.println("Direction has changed to: " + dir);
    }

    public static Enemy createEnemy(Animation animation) {

        // GameState state = GameState.getInstance();

        int health = 3;
        int goldReward = 5;

        Enemy enemy = new Enemy(animation, health, goldReward);
        return enemy;
    }

}

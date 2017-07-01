package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by markus on 01.07.17.
 */

public class Enemy extends Actor {

    protected Animation animatedNpc;
    private int health;
    private int goldReward;

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


}

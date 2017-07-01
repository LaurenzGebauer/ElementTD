package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Laurenz on 28.06.2017.
 */

public class ArrowTower extends Actor {

    Texture region;
    TextureRegion tregion;

    private  int damage = 100;
    private  int range = 100;



    public ArrowTower () {
        region = new Texture(Gdx.files.internal("Hero.png"));
        tregion= new TextureRegion(region);
    }


    public void draw (Batch batch, int x , int y) {
                batch.draw(tregion,x,y);

    }

    public void upgrade() {
         damage=+10;
         range=+10;
    }
}


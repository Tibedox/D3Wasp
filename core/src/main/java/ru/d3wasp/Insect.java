package ru.d3wasp;

import static ru.d3wasp.Main.*;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

abstract public class Insect {
    public float x;
    public float y;
    public float width;
    public float height;
    float stepX;
    float stepY;
    boolean isLeave;
    public Texture img;
    public Sound snd;

    public Insect(float x, float y, Texture img, Sound snd){
        this.x = x;
        this.y = y;
        this.img = img;
        this.snd = snd;
        width = height = MathUtils.random(50, 150);
        stepX = MathUtils.random(-5f, 5);
        stepY = MathUtils.random(-5f, 5);
    }

    public void fly(){
        x += stepX;
        y += stepY;
        if(!isLeave) {
            if (x > SCR_WIDTH - width || x < 0) stepX = -stepX;
            if (y > SCR_HEIGHT - height || y < 0) stepY = -stepY;
        }
    }

    public boolean flip(){
        return stepX<0;
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }

    abstract void leave();
}

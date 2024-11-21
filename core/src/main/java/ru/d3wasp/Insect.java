package ru.d3wasp;

import static ru.d3wasp.Main.SCR_HEIGHT;
import static ru.d3wasp.Main.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

abstract public class Insect {
    public float x;
    public float y;
    public float width;
    public float height;
    float stepX;
    float stepY;
    boolean isLeave;

    public Insect(float x, float y){
        this.x = x;
        this.y = y;
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

    public boolean flipLeave(){
        return isLeave;
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }

    abstract void leave();
}

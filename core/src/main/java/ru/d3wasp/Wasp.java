package ru.d3wasp;

import static ru.d3wasp.Main.*;

import com.badlogic.gdx.math.MathUtils;

public class Wasp {
    public float x;
    public float y;
    public float width;
    public float height;
    private float stepX;
    private float stepY;

    public Wasp(float x, float y){
        this.x = x;
        this.y = y;
        width = height = MathUtils.random(50, 150);
        stepX = MathUtils.random(-5f, 5);
        stepY = MathUtils.random(-5f, 5);
    }

    public void fly(){
        x += stepX;
        y += stepY;
        if(x>SCR_WIDTH - width || x<0) stepX = -stepX;
        if(y>SCR_HEIGHT - height || y<0) stepY = -stepY;
    }

    public boolean flip(){
        return stepX<0;
    }
}

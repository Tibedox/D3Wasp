package ru.d3wasp;

import static ru.d3wasp.Main.*;

import com.badlogic.gdx.math.MathUtils;

public class Wasp {
    float x;
    float y;
    float width;
    float height;
    float stepX;
    float stepY;

    Wasp(float x, float y){
        this.x = x;
        this.y = y;
        width = height = MathUtils.random(50, 150);
        stepX = MathUtils.random(-5, 5);
        stepY = MathUtils.random(-5, 5);
    }

    void fly(){
        x += stepX;
        y += stepY;
        if(x>SCR_WIDTH - width || x<0) stepX = -stepX;
        if(y>SCR_HEIGHT - height || y<0) stepY = -stepY;
    }

    boolean flip(){
        return stepX<0;
    }
}

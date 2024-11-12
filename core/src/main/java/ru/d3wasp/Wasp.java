package ru.d3wasp;

import static ru.d3wasp.Main.*;

public class Wasp {
    float x = 0;
    float y = 100;
    float width = 200;
    float height = 200;
    float stepX = 5;
    float stepY = 3;

    void fly(){
        x += stepX;
        y += stepY;
        if(x>SCR_WIDTH - width || x<0) stepX = -stepX;
        if(y>SCR_HEIGHT - height || y<0) stepY = -stepY;
    }
}

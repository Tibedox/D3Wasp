package ru.d3wasp;


public class Trump extends Insect {
    public Trump(float x, float y){
        super(x, y);
        width = height*1.25f;
    }

    @Override
    void leave() {
        stepX = 0;
        stepY = 10;
        isLeave = true;
    }
}

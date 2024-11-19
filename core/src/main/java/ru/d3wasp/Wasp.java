package ru.d3wasp;


public class Wasp extends Insect {
    public Wasp(float x, float y){
        super(x, y);
    }

    @Override
    void leave() {
        stepX = 0;
        stepY = -10;
        isLeave = true;
    }
}

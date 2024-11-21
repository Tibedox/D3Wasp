package ru.d3wasp;


import com.badlogic.gdx.math.MathUtils;

public class Trump extends Insect {
    float rotation, speedRotation;

    public Trump(float x, float y){
        super(x, y);
        width = height*1.25f;
        speedRotation = MathUtils.random(-20f, 20);
    }

    @Override
    public void fly() {
        super.fly();
        if(isLeave){
            rotation += speedRotation;
        }
    }

    @Override
    void leave() {
        stepX = 0;
        stepY = 10;
        isLeave = true;
    }
}

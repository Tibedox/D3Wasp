package ru.d3wasp;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Wasp extends Insect {
    public Wasp(float x, float y, Texture img, Sound snd){
        super(x, y, img, snd);
    }

    @Override
    void leave() {
        stepX = 0;
        stepY = -10;
        isLeave = true;
        snd.play();
    }
}

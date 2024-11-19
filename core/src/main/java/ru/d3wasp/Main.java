package ru.d3wasp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;

    private SpriteBatch batch;
    SB2 batch2;
    private Texture imgBackGround;
    private Texture imgWasp;
    private Texture imgTrump;

    private Wasp[] wasp = new Wasp[33];
    private Trump[] trump = new Trump[22];

    @Override
    public void create() {
        batch = new SpriteBatch();
        batch2 = new SB2();

        imgBackGround = new Texture("bg.png");
        imgWasp = new Texture("wasp.png");
        imgTrump = new Texture("trump.png");

        for (int i = 0; i < wasp.length; i++) {
            wasp[i] = new Wasp(SCR_WIDTH/2, SCR_HEIGHT/2);
        }
        for (int i = 0; i < trump.length; i++) {
            trump[i] = new Trump(0, 0);
        }
    }

    @Override
    public void render() {
        // события
        for (int i = 0; i < wasp.length; i++) {
            wasp[i].fly();
        }
        // for(Wasp w: wasp) w.fly();
        for (int i = 0; i < trump.length; i++) {
            trump[i].fly();
        }

        // отрисовка
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (int i = 0; i < wasp.length; i++) {
            batch.draw(imgWasp, wasp[i].x, wasp[i].y, wasp[i].width, wasp[i].height, 0, 0, 200, 200, wasp[i].flip(), false);
        }
        for (int i = 0; i < trump.length; i++) {
            batch.draw(imgTrump, trump[i].x, trump[i].y, trump[i].width, trump[i].height, 0, 0, 250, 200, trump[i].flip(), false);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgBackGround.dispose();
        imgWasp.dispose();
        imgTrump.dispose();
    }
}

class SB2 extends SpriteBatch{

}

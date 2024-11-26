package ru.d3wasp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1600;
    public static final float SCR_HEIGHT = 900;
    public static final float SPAWN_WASP_X = 390;
    public static final float SPAWN_WASP_Y = 595;
    public static final float SPAWN_TRUMP_X = 1126;
    public static final float SPAWN_TRUMP_Y = 750;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;

    private Texture imgBackGround;
    private Texture imgWasp;
    private Texture imgTrump;
    private Sound sndWasp;
    private Sound sndTrump;

    private Wasp[] wasp = new Wasp[33];
    private Trump[] trump = new Trump[22];

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();

        imgBackGround = new Texture("bg2.jpg");
        imgWasp = new Texture("wasp.png");
        imgTrump = new Texture("trump.png");
        sndWasp = Gdx.audio.newSound(Gdx.files.internal("wasp.mp3"));
        sndTrump = Gdx.audio.newSound(Gdx.files.internal("trump2.mp3"));

        for (int i = 0; i < wasp.length; i++) {
            wasp[i] = new Wasp(SPAWN_WASP_X, SPAWN_WASP_Y, imgWasp, sndWasp);
        }
        for (int i = 0; i < trump.length; i++) {
            trump[i] = new Trump(SPAWN_TRUMP_X, SPAWN_TRUMP_Y, imgTrump, sndTrump);
        }
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            System.out.println(touch.x+" "+touch.y);
            for (Wasp w: wasp) {
                if(w.hit(touch.x, touch.y)) {
                    w.leave();
                }
            }
            for (Trump t: trump) {
                if(t.hit(touch.x, touch.y)) {
                    t.leave();
                }
            }
        }

        // события
        for (Wasp w: wasp) w.fly();
        for (Trump t: trump) t.fly();

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for(Wasp w: wasp){
            batch.draw(w.img, w.x, w.y, w.width, w.height, 0, 0, w.img.getWidth(), w.img.getHeight(), w.flip(), w.isLeave);
        }
        for (Trump t: trump) {
            batch.draw(t.img, t.x, t.y, t.width/2, t.height/2, t.width, t.height, 1, 1, t.rotation, 0, 0, t.img.getWidth(), t.img.getHeight(), t.flip(), t.isLeave);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgBackGround.dispose();
        imgWasp.dispose();
        imgTrump.dispose();
        sndWasp.dispose();
        sndTrump.dispose();
    }
}

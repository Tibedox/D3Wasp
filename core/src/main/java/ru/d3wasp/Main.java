package ru.d3wasp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

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

    private BitmapFont font40, font55, font70, font90;

    private Texture imgBackGround;
    private Texture imgWasp;
    private Texture imgTrump;
    private Sound sndWasp;
    private Sound sndTrump;

    WaspButton btnRestart;

    private Wasp[] wasp = new Wasp[1];
    private Trump[] trump = new Trump[1];
    private Player[] player = new Player[6];
    private int counterInsects;
    private long timeStartGame;
    private long timeCurrent;
    private boolean isGameOver;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();

        font40 = new BitmapFont(Gdx.files.internal("font/stylo40.fnt"));
        font55 = new BitmapFont(Gdx.files.internal("font/stylo55.fnt"));
        font70 = new BitmapFont(Gdx.files.internal("font/stylo70.fnt"));
        font90 = new BitmapFont(Gdx.files.internal("font/stylo90.fnt"));
        imgBackGround = new Texture("bg2.jpg");
        imgWasp = new Texture("wasp.png");
        imgTrump = new Texture("trump.png");
        sndWasp = Gdx.audio.newSound(Gdx.files.internal("wasp.mp3"));
        sndTrump = Gdx.audio.newSound(Gdx.files.internal("trump2.mp3"));

        btnRestart = new WaspButton(font70, "RESTART", 640, 150);

        for (int i = 0; i < wasp.length; i++) {
            wasp[i] = new Wasp(SPAWN_WASP_X, SPAWN_WASP_Y, imgWasp, sndWasp);
        }
        for (int i = 0; i < trump.length; i++) {
            trump[i] = new Trump(SPAWN_TRUMP_X, SPAWN_TRUMP_Y, imgTrump, sndTrump);
        }
        for (int i = 0; i < player.length; i++) {
            player[i] = new Player("Noname", 0);
        }
        loadTableOfRecords();
        timeStartGame = TimeUtils.millis();
    }

    @Override
    public void render() {
        // касания
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            for (Wasp w : wasp) {
                if (w.hit(touch.x, touch.y)) {
                    w.leave();
                    counterInsects++;
                }
            }
            for (Trump t : trump) {
                if (t.hit(touch.x, touch.y)) {
                    t.leave();
                    counterInsects++;
                }
            }
            if (!isGameOver && counterInsects == trump.length + wasp.length) {
                gameOver();
            }
            if(isGameOver){
                if(btnRestart.hit(touch.x, touch.y)){
                    gameRestart();
                }
            }
        }

        // события
        for (Wasp w : wasp) w.fly();
        for (Trump t : trump) t.fly();
        if (!isGameOver) timeCurrent = TimeUtils.millis() - timeStartGame;

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (Wasp w : wasp) {
            batch.draw(w.img, w.x, w.y, w.width, w.height, 0, 0, w.img.getWidth(), w.img.getHeight(), w.flip(), w.isLeave);
        }
        for (Trump t : trump) {
            batch.draw(t.img, t.x, t.y, t.width/2, t.height/2, t.width, t.height, 1, 1, t.rotation, 0, 0, t.img.getWidth(), t.img.getHeight(), t.flip(), t.isLeave);
        }
        font55.draw(batch, "Сбито: " + counterInsects, 10, SCR_HEIGHT - 10);
        font55.draw(batch, showTime(timeCurrent), SCR_WIDTH - 190, SCR_HEIGHT - 10);
        if(isGameOver) {
            font90.draw(batch, "Game Over", 0, 700, SCR_WIDTH, Align.center, true);
            for (int i = 0; i < player.length-1; i++) {
                font70.draw(batch, player[i].name, 450, 550 - 70*i);
                font70.draw(batch, showTime(player[i].time), 900, 550 - 70*i);
            }
            btnRestart.font.draw(batch, btnRestart.text, btnRestart.x, btnRestart.y);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font40.dispose();
        font55.dispose();
        font70.dispose();
        font90.dispose();
        imgBackGround.dispose();
        imgWasp.dispose();
        imgTrump.dispose();
        sndWasp.dispose();
        sndTrump.dispose();
    }

    private String showTime(long time) {
        long msec = time % 1000;
        long sec = time / 1000 % 60;
        long min = time / 1000 / 60 % 60;
        //long hour = time/1000/60/60%24;
        return min / 10 + min % 10 + ":" + sec / 10 + sec % 10 + ":" + msec / 100;
    }

    private void sortTableOfRecords(){
        for (Player p : player) {
            if (p.time == 0) p.time = Long.MAX_VALUE;
        }

        int n = player.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (player[j].time > player[j + 1].time) {
                    // Меняем элементы местами
                    Player temp = player[j];
                    player[j] = player[j + 1];
                    player[j + 1] = temp;
                }
            }
        }

        for (Player p : player) {
            if (p.time == Long.MAX_VALUE) p.time = 0;
        }
    }

    private void saveTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("WaspTable");
        for (int i = 0; i < player.length; i++) {
            prefs.putString("name"+i, player[i].name);
            prefs.putLong("time"+i, player[i].time);
        }
        prefs.flush();
    }

    private void loadTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("WaspTable");
        for (int i = 0; i < player.length; i++) {
            player[i].name = prefs.getString("name"+i, "Noname");
            player[i].time = prefs.getLong("time"+i, 0);
        }
    }

    private void gameOver(){
        isGameOver = true;
        player[player.length-1].name = "Winner";
        player[player.length-1].time = timeCurrent;
        sortTableOfRecords();
        saveTableOfRecords();
    }

    private void gameRestart(){
        isGameOver = false;
        counterInsects = 0;
        for (int i = 0; i < wasp.length; i++) {
            wasp[i] = new Wasp(SPAWN_WASP_X, SPAWN_WASP_Y, imgWasp, sndWasp);
        }
        for (int i = 0; i < trump.length; i++) {
            trump[i] = new Trump(SPAWN_TRUMP_X, SPAWN_TRUMP_Y, imgTrump, sndTrump);
        }
        timeStartGame = TimeUtils.millis();
    }
}


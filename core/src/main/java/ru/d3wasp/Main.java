package ru.d3wasp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;
    private SpriteBatch batch;
    private Texture image;
    Wasp wasp0, wasp1, wasp2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("wasp.png");
        wasp0 = new Wasp(0, 0);
        wasp1 = new Wasp(500, 200);
        wasp2 = new Wasp(100, 300);
    }

    @Override
    public void render() {
        wasp0.fly();
        wasp1.fly();
        wasp2.fly();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, wasp0.x, wasp0.y, wasp0.width, wasp0.height, 0, 0, 200, 200, wasp0.flip(), false);
        batch.draw(image, wasp1.x, wasp1.y, wasp1.width, wasp1.height, 0, 0, 200, 200, wasp1.flip(), false);
        batch.draw(image, wasp2.x, wasp2.y, wasp2.width, wasp2.height, 0, 0, 200, 200, wasp2.flip(), false);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}

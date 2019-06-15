package ru.geekbrains.stargame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture imgBackground;
    private Texture imgShuttle;
    private TextureRegion regionTest;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 speed;

    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("space.jpg");
        imgShuttle =new Texture("shuttle.jpg");
        regionTest = new TextureRegion(imgShuttle,0,0,64,64);
        touch=new Vector2();
        pos=new Vector2();
        speed=new Vector2(0.1f,0.2f);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0.56f, 0.44f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgBackground, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(imgShuttle, 10, 10,128,128);
        batch.draw(regionTest, 200, 10,128,128);
        batch.end();
    }

    @Override
    public void dispose() {
        imgShuttle.dispose();
        imgBackground.dispose();
        super.dispose();

    }


}


package ru.geekbrains.stargame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.BaseScreen;
import ru.geekbrains.stargame.Sprite.Background;
import ru.geekbrains.stargame.Sprite.Shuttle;
import ru.geekbrains.stargame.math.Rect;

public class MenuScreen extends BaseScreen {
    private Texture imgBackground;
    private Texture imgShuttle;


    private Background background;

    private Vector2 buffer;


    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("background.jpg");
        imgShuttle = new Texture("badlogic.jpg");

        pos = new Vector2(); //вектор позиции
        speed = new Vector2();//вектор скорости

        buffer = new Vector2();//буферный вектор для контроля прохождения точки нажатия
        background = new Background(new TextureRegion(imgBackground));
        shuttle = new Shuttle(new TextureRegion(imgShuttle));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        shuttle.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.56f, 0.44f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        shuttle.draw(batch);
        //shuttle.resize(worldBounds);
        batch.end();

        //v.set(worldBounds.getLeft(), worldBounds.getBottom());
        buffer.set(touch);
        if (buffer.sub(shuttle.pos).len() > V_LEN) {

            shuttle.pos.add(speed);
        } else {
            shuttle.pos.set(touch);
        }
    }

    @Override
    public void dispose() {
        imgBackground.dispose();
        imgShuttle.dispose();
        super.dispose();

    }
}


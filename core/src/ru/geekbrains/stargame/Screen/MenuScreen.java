package ru.geekbrains.stargame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.BaseScreen;

public class MenuScreen extends BaseScreen {
   // private Texture imgBackground;
    private Texture imgShuttle;
    private static float V_LEN = 0.5f;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 speed;
    private Vector2 buffer;


    @Override
    public void show() {
        super.show();
     //   imgBackground = new Texture("space.jpg");
        imgShuttle = new Texture("shuttle.jpg");
        System.out.println(imgShuttle.getHeight());
        touch = new Vector2(); //вектор направления
        pos = new Vector2(); //вектор позиции
        speed = new Vector2();//вектор скорости
        buffer = new Vector2();//буферный вектор для контроля прохождения точки нажатия
        batch.getProjectionMatrix().idt();
       // System.out.println("Gdx.graphics.getWidth() = " + Gdx.graphics.getWidth());
       // System.out.println("Gdx.graphics.getHeight() = " + Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.56f, 0.44f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
      //  batch.draw(imgBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(imgShuttle, -0.5f, -0.5f,1f,1f);
        batch.end();
        buffer.set(touch);
        if (buffer.sub(pos).len() > V_LEN) {
            pos.add(speed);
        } else {
            pos.set(touch);
        }
       // System.out.printf("touch.x=%f   touch.y=%f   pos.x=%f   pos.y=%f  speed.x=%f  speed.y=%f\n", touch.x, touch.y, pos.x, pos.y, speed.x, speed.y);
    }

    @Override
    public void dispose() {
        imgShuttle.dispose();
       //imgBackground.dispose();
        super.dispose();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        speed.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {//обработка нажатия курсоров
        switch (keycode) {
            case (19): //вверх
                touch.y++;
                break;
            case (20)://вниз
                touch.y--;
                break;
            case (21)://влево
                touch.x--;
                break;
            case (22)://вправо
                touch.x++;
                break;
        }
        speed.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {//при нажатой кнопки мышки картинка двигается в след за курсором
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        speed.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }
}


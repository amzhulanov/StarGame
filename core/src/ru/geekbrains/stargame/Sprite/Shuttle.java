package ru.geekbrains.stargame.Sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Shuttle extends Sprite {

    private Vector2 speed;
    private Vector2 buffer;
    private Vector2 touch;
    private Rect worldBounds;
    private static float V_LEN = 0.01f;

    public Shuttle(TextureRegion region) {
        super(region);
        setHeightProportion(0.4f);
        speed=new Vector2();//вектор скорости
        buffer=new Vector2();//буферный вектор
        touch=new Vector2();//вектор нажатия
    }

    @Override
    public void update(float delta) {
        if (worldBounds.getBottom()>getBottom()){
            speed.setZero();
            setBottom(worldBounds.getBottom());
            return;
        }

        buffer.set(touch);
        if (buffer.sub(pos).len()>V_LEN){
            pos.add(speed);
        }else {
            pos.set(touch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch.set(touch);
        speed=touch.cpy().sub(pos);
        speed.setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        this.touch.set(touch);
        speed=touch.cpy().sub(pos);
        speed.setLength(V_LEN);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }
}

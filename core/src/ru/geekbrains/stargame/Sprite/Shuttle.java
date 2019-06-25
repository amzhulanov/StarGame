package ru.geekbrains.stargame.Sprite;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

import static ru.geekbrains.stargame.Base.ScaledTouchUpButton.INDENT;

public class Shuttle extends Sprite {

    private Vector2 speed;
    private Vector2 buffer;
    private Vector2 touch;
    private Rect worldBounds;
    private static float V_LEN = 0.001f;

    public Shuttle(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        setHeightProportion(0.4f);
        speed=new Vector2();//вектор скорости
        buffer=new Vector2();//буферный вектор
        speed.set(0,0);
    }

        @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        /*this.touch.set(touch);
        speed=touch.cpy().sub(pos);
        speed.setLength(V_LEN);*/
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = 0;//worldBounds.getHalfWidth();
        float posY = worldBounds.getBottom()+INDENT;
        pos.set(posX, posY);
    }

    public void updateShuttle(float delta){
        speed.add(V_LEN*delta,0);
        buffer.set(pos);
        if (buffer.add(speed).x>worldBounds.getLeft() &&
                buffer.add(speed).x<worldBounds.getRight()) {
            pos.add(speed);
        } else {
            pos.set(pos);
            speed.set(0,0);

        }
        System.out.println("updateShuttle pos.x "+pos.x+ "   worldBounds.getLeft="+worldBounds.getLeft()+"   worldBounds.getRight="+worldBounds.getRight()+"   speed="+speed.x);
    }

}

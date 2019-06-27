package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.BulletPool;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureAtlas atlas;

    private final Vector2 v0 = new Vector2(0.5f, 0f);//статичная скорость
    private Vector2 v = new Vector2();


    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("enemy0"), 1, 2, 2);
        this.atlas = atlas;
        v.set(0.05f,0);
        //this.bulletPool = bulletPool;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setTop(worldBounds.getTop()-0.05f);
    }

    @Override
    public void update(float delta) {

        //if (getRight()>worldBounds.getHalfWidth())
        //pos.mulAdd(v, delta);
        if (getRight() < worldBounds.getRight() &&v.x>0) {
            this.pos.add(v);
            return;
        }
        if (getRight()>= worldBounds.getRight() &&v.x>0) {
            setRight(worldBounds.getRight());
            v.set(Rnd.nextFloat(-0.001f, -0.008f),0);
            this.pos.add(v);
            return;
        }
        if (getLeft() > worldBounds.getLeft() &&v.x<0) {
            pos.add(v);
        }
        if (getLeft()<= worldBounds.getLeft() &&v.x<0) {
            setLeft(worldBounds.getLeft());
            v.set(Rnd.nextFloat(0.001f, 0.008f),0);
            pos.add(v);

        }
    }
}

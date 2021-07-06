package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class EnemyShip extends Ship {

    public EnemyShip(BulletPool bulletPool, Sound shootSound, Rect worldbounds) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.worldBounds = worldbounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();

        }

    }

    public void set(
            TextureRegion[] regions,//две текстуры для двух разных состояний
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHigh,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height

    ) {
        this.regions = regions;//вид корабля
        this.v0.set(v0);//начальная скорость корабля
        this.bulletRegion = bulletRegion;//вид пули
        this.bulletHigh = bulletHigh;//размер пули
        this.bulletV.set(0, bulletVY);//скорость пули
        this.damage = damage;//сила повреждения
        this.reloadInterval = reloadInterval;//интервал перезагрузки
        this.hp = hp;//кол-во жизней
        setHeightProportion(height);
        v.set(v0);


    }


}

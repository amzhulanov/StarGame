package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public abstract class Ship extends Sprite {

    protected Vector2 v0;
    protected Vector2 v;
    protected Vector2 bulletV;
    protected float bulletHigh;
    protected int damage;
    protected int hp;
    protected int score;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Rect worldBounds;

    protected TextureRegion bulletRegion;
    protected boolean doubleBullet;
    protected Sound shootSound;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;

        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }


    public void damage(int damage) {
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damage;
        if (hp <= 0) {
            destroy();
            hp = 0;
        }
    }

    public int getHp() {
        return hp;
    }

    protected void shoot(Boolean doubleBullet) {
        Vector2 delta = new Vector2(getHalfWidth()-0.02f, 0);
        Bullet bullet;
        if (doubleBullet) {
            System.out.println(pos.x + " - " + pos.y);
            System.out.println(pos.cpy().sub(delta).x);
            bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos.cpy().sub(delta), bulletV, bulletHigh, worldBounds, damage);
            bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos.cpy().add(delta), bulletV, bulletHigh, worldBounds, damage);
            shootSound.play();
        } else {
            bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, bulletHigh, worldBounds, damage);
            shootSound.play();
        }
    }

    protected void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public Vector2 getV() {
        return v;
    }

    public int getScore() {
        return score;
    }
}

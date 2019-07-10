package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private enum State{DESCENT,FIGHT}

    private State state;
    private Vector2 descentV = new Vector2(0, -0.15f);//начальная скорость для вылета корабля из-за экрана

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldbounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldbounds;
        this.v = new Vector2();
        this.v0 = new Vector2(0,-0.15f);
        this.bulletV = new Vector2();
        state=State.DESCENT;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch(state){
            case DESCENT:
                if (getTop() < worldBounds.getTop()) {
                    v.set(v0);
                    state=state.FIGHT;
                }
                break;
            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    System.out.println(this.doubleBullet);
                    if (this.doubleBullet) {
                        shoot(this.doubleBullet);
                    }else{shoot(this.doubleBullet);}
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHigh,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height,
            boolean doubleBullet,
            int score
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
        this.doubleBullet=doubleBullet;
        this.score=score;
        v.set(descentV);
        reloadTimer=reloadInterval;
        state=state.DESCENT;


    }


    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight()<getLeft()
        || bullet.getLeft()>getRight()
        || bullet.getBottom()>getTop()
        || bullet.getTop()<pos.y);
    }



}

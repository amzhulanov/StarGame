package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.ScaledTouchUpButton;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

import static ru.geekbrains.stargame.sprite.MainShip.HP_DEFAULT;

public class ButtonNewGame extends ScaledTouchUpButton {

    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;


    public ButtonNewGame(TextureAtlas atlas, MainShip mainShip, BulletPool bulletPool, EnemyPool enemyPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("button_new_game"));
        this.mainShip = mainShip;
        this.bulletPool = bulletPool;
        this.enemyPool = enemyPool;
        this.explosionPool = explosionPool;


    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setLeft(worldBounds.getLeft() + 0.03f);
        setBottom(worldBounds.getBottom() + 0.03f);
    }

    @Override
    public void action() {
        mainShip.flushDestroy();
        mainShip.hp = HP_DEFAULT;
        mainShip.v0 = new Vector2(0.5f, 0f);//хадаю первоначальный вектор скорости
        mainShip.setLeft(0);

        mainShip.stop();
        mainShip.pressedRight = false;
        mainShip.pressedRight = false;

        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
    }

}

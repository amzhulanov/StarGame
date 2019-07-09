package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.EnemyShip;

public class EnemyGenerator {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MIDDLE_HEIGHT = 0.1f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.25f;
    private static final int ENEMY_MIDDLE_DAMAGE = 5;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MIDDLE_HP = 5;


    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;



    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;
    private Vector2 enemySmallV;
    private Vector2 enemyMiddleV;
    private Vector2 enemyBigV;

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    private float generateInterval = 4f; //интервал генерации кораблей
    private float generateTimer;

    private Rect worldBounds;
    public int level;

    public EnemyGenerator(TextureAtlas atlas, EnemyPool enemyPool,Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.enemySmallV = new Vector2(0f, -0.2f);
        this.enemyMiddleV = new Vector2(0f, -0.03f);
        this.enemyBigV = new Vector2(0f, -0.05f);
        this.worldBounds=worldBounds;
        TextureRegion region0 = atlas.findRegion("enemy0");
        enemySmallRegion = Regions.split(region0, 1, 2, 2);//текстура для корабля
        TextureRegion region1 = atlas.findRegion("enemy1");
        enemyMiddleRegion = Regions.split(region1, 1, 2, 2);//текстура для корабля
        TextureRegion region2 = atlas.findRegion("enemy2");
        enemyBigRegion = Regions.split(region2, 1, 2, 2);//текстура для корабля

        bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta,int frags) {
        level=frags/10+1;
        generateTimer += delta;
        if (generateTimer > generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type=(float)Math.random();
            if (type<0.5f){
                enemyShip.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE*level,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT

                );
            }else if (type<0.8f){
                enemyShip.set(
                        enemyMiddleRegion,
                        enemyMiddleV,
                        bulletRegion,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        ENEMY_MIDDLE_BULLET_VY,
                        ENEMY_MIDDLE_DAMAGE*level,
                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        ENEMY_MIDDLE_HP,
                        ENEMY_MIDDLE_HEIGHT);
            }else{
                enemyShip.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE*level,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_HEIGHT) ;
            }
            enemyShip.pos.x= Rnd.nextFloat(worldBounds.getLeft()+enemyShip.getHalfWidth(),worldBounds.getRight()-enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());

        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

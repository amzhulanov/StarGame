package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.ButtonNewGame;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.GameOver;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;

    private Star[] starArray;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyGenerator enemyGenerator;

    private MainShip mainShip;

    protected Sound laserSound;
    protected Sound bulletSound;
    protected Sound explosionSound;

    private GameOver gameOver;

    private ButtonNewGame buttonNewGame;
    private Game game;
    private GameScreen gameScreen;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound= Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < starArray.length; i++) {
            starArray[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool=new ExplosionPool(atlas,explosionSound);
        enemyPool = new EnemyPool(bulletPool,explosionPool, bulletSound,  worldBounds);

        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);

        musicOn("sounds/GameScreen.mp3", 0.1f, true);

        gameOver=new GameOver(atlas);

        buttonNewGame = new ButtonNewGame(atlas,mainShip,bulletPool,enemyPool,explosionPool);
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        update(delta);
        collisionShip();
        freeAllDestroyedSprites();
        draw();
    }

    public void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta);
        }
        explosionPool.updateActiveSprites(delta);

    }

    public void collisionShip() {
        if (mainShip.isDestroyed()) {

            return;
        }
        List<EnemyShip> enemyShipsList=enemyPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipsList) {
            if (enemy.isDestroyed()){
                continue;
            }
            float minDist=enemy.getHalfWidth()+mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos)<minDist){
                enemy.destroy();//корабль уничтожается
                mainShip.destroy();
            }
        }
        List<Bullet> bulletList=bulletPool.getActiveObjects();
        for(Bullet bullet:bulletList){
            if(bullet.isDestroyed()){
                continue;
            }
            if (bullet.getOwner()==mainShip) {
                for (EnemyShip enemyShip : enemyShipsList) {
                    if (enemyShip.isDestroyed()) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();

                    }

                }
            }else {
                if (mainShip.isBulletCollision(bullet)){
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }

            }
        }
    }

    public void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }else{
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }


        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();

        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        }else {
            buttonNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer);
        }else{
            buttonNewGame.touchUp(touch, pointer);
        }
        return false;
    }

}

package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

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
import ru.geekbrains.stargame.sprite.TrackingStar;
import ru.geekbrains.stargame.utils.EnemyGenerator;
import ru.geekbrains.stargame.utils.Font;

public class GameScreen extends BaseScreen {

    private enum State {PLAY, PAUSE, GAMEOVER}

    private static final int STAR_COUNT = 64;
    private static final String FRAGS = "Frags:";
    private static final String HP = "HP:";
    private static final String Level = "Level:";

    private Font font;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas superShip;

    private TrackingStar[] starArray;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyGenerator enemyGenerator;
    private EnemyGenerator enemyGeneratorHighLevel;

    private MainShip mainShip;

    protected Sound laserSound;
    protected Sound bulletSound;
    protected Sound explosionSound;

    private GameOver gameOver;

    private ButtonNewGame buttonNewGame;

    private State state;
    private State oldState;

    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;


    @Override
    public void show() {
        super.show();
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.03f);

        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        //superShip = new TextureAtlas("textures/atlasLevel.tpack");
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        starArray = new TrackingStar[STAR_COUNT];

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds);

        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
        enemyGeneratorHighLevel = new EnemyGenerator(atlas, enemyPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        for (int i = 0; i < starArray.length; i++) {
            starArray[i] = new TrackingStar(atlas, mainShip.getV());
        }

        musicOn("sounds/GameScreen.mp3", 0.1f, true);
        gameOver = new GameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();


        state = State.PLAY;
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
        if (state == State.PLAY) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            switch (enemyGenerator.getLevel()) {
                case (2):
                    enemyGenerator.generate(delta, frags);
                    break;
                case (3):
                    enemyGenerator.generate(delta, frags);
                    break;
                default:
                    enemyGenerator.generate(delta, frags);
                    break;

            }
        }
        explosionPool.updateActiveSprites(delta);

    }

    public void collisionShip() {
        if (state != State.PLAY) {
            return;
        }
        List<EnemyShip> enemyShipsList = enemyPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipsList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.destroy();//корабль уничтожается
                mainShip.damage(mainShip.getHp());
                state = State.GAMEOVER;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == mainShip) {
                for (EnemyShip enemyShip : enemyShipsList) {
                    if (enemyShip.isDestroyed()) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        if (enemyShip.isDestroyed()) {
                            frags = frags + enemyShip.getScore();
                        }
                        bullet.destroy();
                    }
                }
            } else {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    if (mainShip.isDestroyed()) {
                        state = State.GAMEOVER;
                    }
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
        if (state == State.PLAY) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAMEOVER) {
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }


        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(Level).append(enemyGenerator.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);


    }

    @Override
    public void pause() {
        super.pause();
        oldState = state;
        state = State.PAUSE;
        music.pause();

    }

    @Override
    public void resume() {
        super.resume();
        state = oldState;
        music.play();
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
        font.dispose();
        bg.dispose();
        atlas.dispose();
        superShip.dispose();
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
        if (state == State.PLAY) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAY) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAY) {
            mainShip.touchDown(touch, pointer);
        } else if (state == State.GAMEOVER) {
            buttonNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAY) {
            mainShip.touchUp(touch, pointer);
        } else if (state == State.GAMEOVER) {
            buttonNewGame.touchUp(touch, pointer);
        }
        return false;
    }

    public void startNewGame() {
        mainShip.setToNewGame(worldBounds);
        state = State.PLAY;
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        frags = 0;
        enemyGenerator.setLevel(1);
    }
}

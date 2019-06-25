package ru.geekbrains.stargame.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.geekbrains.stargame.Base.BaseScreen;
import ru.geekbrains.stargame.Sprite.Background;
import ru.geekbrains.stargame.Sprite.ButtonExit;
import ru.geekbrains.stargame.Sprite.ButtonPlay;
import ru.geekbrains.stargame.Sprite.SpaceGarbage.Star;
import ru.geekbrains.stargame.Sprite.SpaceGarbage.StarEmitter;
import ru.geekbrains.stargame.math.Rect;

public class MenuScreen extends BaseScreen {

    private Game game;
    private Texture imgBackground;
    private Background background;
    private TextureAtlas atlas;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;


    private Array<Star> stars = new Array<Star>();

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("textures/background/bg.png");
        background = new Background(new TextureRegion(imgBackground));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        StarEmitter.initialize();
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        StarEmitter.run(stars,worldBounds);
        super.render(delta);
        update(delta);
        draw();

    }

    private void update(float delta) {
        for (int i=stars.size;--i>=0;) {
            stars.get(i).update(delta);
            if (!stars.get(i).isActive){
                StarEmitter.clean(stars,i);
            }
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        imgBackground.dispose();
        atlas.dispose();
        super.dispose();

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return false;
    }
}


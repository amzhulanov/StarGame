package ru.geekbrains.stargame.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.geekbrains.stargame.Base.BaseScreen;
import ru.geekbrains.stargame.Sprite.Background;
import ru.geekbrains.stargame.Sprite.Shuttle;
import ru.geekbrains.stargame.Sprite.SpaceGarbage.Asteroid;
import ru.geekbrains.stargame.Sprite.SpaceGarbage.AsteroidEmitter;
import ru.geekbrains.stargame.Sprite.SpaceGarbage.Meteor;
import ru.geekbrains.stargame.math.Rect;

public class GameScreen extends BaseScreen {

    private Background background;
    private Texture imgBackground;
    private Meteor meteor;
    private Shuttle shuttle;

    private TextureAtlas atlas;
    private TextureAtlas atlasShip;

    private Array<Asteroid> asteroids = new Array<Asteroid>();

    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("textures/background/space.jpg");
        background = new Background(new TextureRegion(imgBackground));
        atlas = new TextureAtlas("textures/SpaceGarbage/pictures/spacegarbage.pack");
        atlasShip = new TextureAtlas("textures/mainAtlas.tpack");
        meteor=new Meteor(atlas);
        shuttle=new Shuttle(atlasShip);
        AsteroidEmitter.initialize();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        AsteroidEmitter.run(asteroids,worldBounds);
        update(delta);
        shuttle.updateShuttle(0);
        draw();
    }

    private void update(float delta) {
        meteor.update(delta);
        for (int i=asteroids.size;--i>=0;) {
            asteroids.get(i).update(delta);
            if (!asteroids.get(i).isActive){
                AsteroidEmitter.clean(asteroids,i);
            }
        }
    }


    private void draw() {
        batch.begin();
        background.draw(batch);
        meteor.draw(batch);
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(batch);
        }
        shuttle.draw(batch);
        batch.end();
    }
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        meteor.resize(worldBounds);
        for (Asteroid asteroid : asteroids) {
            asteroid.resize(worldBounds);
        }
        shuttle.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        imgBackground.dispose();
        atlas.dispose();
        atlasShip.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
           /* case (19): //вверх
                touch.y++;
                break;
            case (20)://вниз
                touch.y--;
                break;*/
            case (21)://влево
                shuttle.updateShuttle(-1);
                break;
            case (22)://вправо
                shuttle.updateShuttle(1);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}

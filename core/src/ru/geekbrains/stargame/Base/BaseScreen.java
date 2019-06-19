package ru.geekbrains.stargame.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import ru.geekbrains.stargame.math.MatrixUtils;
import ru.geekbrains.stargame.math.Rect;


public abstract class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        screenBounds=new Rect();
        worldBounds=new Rect();
        glBounds=new Rect(0,0,1f,1f);

        worldToGl=new Matrix4();
    }

    @Override
    public void render(float delta) {

    }



    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect=width/ (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f*aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl,worldBounds,glBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);

    }

    public void resize(Rect worldBounds) {
        System.out.println("worldBounds width = " + worldBounds.getWidth() + " worldBounds = " + worldBounds.getHeight());
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }
    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();

    }
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled amount = " + amount);

        return false;
    }

}

package ru.geekbrains.stargame.Base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Sprite.Shuttle;
import ru.geekbrains.stargame.math.MatrixUtils;
import ru.geekbrains.stargame.math.Rect;


public abstract class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    protected Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;
    protected Vector2 touch;
    protected Vector2 pos;
    protected Vector2 v;
    protected static float V_LEN = 0.01f;


    private Matrix4 worldToGl; //используется в batch по умолчанию
    private Matrix3 screenToWorld;//используем для преобразования из скриновской в мировую систему координат

    protected Vector2 speed;
    public Shuttle shuttle;

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);


        touch = new Vector2(); //вектор направления
        v = new Vector2();//буферный вектор позиции

        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
    }

    @Override
    public void render(float delta) {

    }


    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
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
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);//вектор touch принимает значение нашей координатной сетки, к которой мы пришли
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {//преобразовываю из скриновской в мировую систему координат
        //System.out.println("touchDown touchX = " + touch.x + " touchY= " + touch.y);
        //System.out.println("worldBounds worldBounds.getLeft = " + worldBounds.getLeft() + " worldBounds.getBottom= " + worldBounds.getBottom()+" worldBounds.getWidth="+worldBounds.getWidth());
        v.set(worldBounds.getLeft(), worldBounds.getBottom());//вектор позиции
        speed.set(touch.cpy().sub(v)).setLength(V_LEN);//скорость
        //System.out.println("touchDown speedX = " + speed.x + " speedY= " + speed.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //  System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        // System.out.println("touchUp touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touchX = " + touch.x + " touchY = " + touch.y);
        worldBounds.setLeft(touch.x);
        worldBounds.setBottom(touch.y);
        shuttle.resize(worldBounds);
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

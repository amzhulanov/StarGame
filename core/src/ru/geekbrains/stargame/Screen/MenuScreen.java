package ru.geekbrains.stargame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture imgBackground;
    private Texture imgShuttle;
    private TextureRegion regionTest;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 speed;
    private Vector2 road;
    private float speedDelta;
    private float xRes;
    private float yRes;


    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("space.jpg");
        imgShuttle =new Texture("shuttle.jpg");
        System.out.println(imgShuttle.getHeight());
        regionTest = new TextureRegion(imgShuttle,0,0,64,64);
        touch=new Vector2(); //вектор направления
        pos=new Vector2(); //вектор позиции
        speed=new Vector2(0.1f,0.2f);//вектор скорости
        speedDelta=1.0f;//скаляр для изменения скорости по событию Scroll
        xRes=100;// первоначальные целевые координаты движения картинки
        yRes=100;// первоначальные целевые координаты движения картинки
        road=new Vector2(xRes/100,yRes/100);
        System.out.println("Gdx.graphics.getWidth() = "+Gdx.graphics.getWidth());
        System.out.println("Gdx.graphics.getHeight() = "+Gdx.graphics.getHeight());
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0.56f, 0.44f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgBackground, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(imgShuttle, pos.x, pos.y);
        batch.draw(regionTest,200,10);
        batch.end();


        if (Math.round(pos.x)!=xRes && Math.round(pos.y)!=yRes){
            pos.add(speed);
        }else if (Math.round(pos.x)!=xRes && Math.round(pos.x+1)!=xRes){ //две координаты, т.к. на большой скорости можно проскочить нужную точку
            pos.add(speed.x,0);
        }else if (Math.round(pos.y)!=yRes && Math.round(pos.y+1)!=yRes){
            pos.add(0,speed.y);
        }
        System.out.printf("xRes=%f    yRes=%f   speed.x=%f   speed.y=%f   pos.x=%f   pos.y=%f \n",xRes,yRes,speed.x,speed.y,pos.x,pos.y);
    }

    @Override
    public void dispose() {
        imgShuttle.dispose();
        imgBackground.dispose();
        super.dispose();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        xRes= screenX;
        yRes=Gdx.graphics.getHeight() - screenY;
        System.out.printf("Новые целевые координаты картинки xRes=%f yRes=%f\n",xRes,yRes);
        orientation();
        return false;
    }

    @Override
    public boolean scrolled(int amount) {//скролл меняет скорость движения

        if (amount==-1 && speedDelta<2){//колёсико крутится вверх
            speedDelta=speedDelta+0.0005f;
            System.out.println("speedDelta-amount = "+(speedDelta+amount));
        }else if (amount==1 && speedDelta>0.01){//колёсико крутится вниз
            speedDelta=speedDelta-0.0005f;
            System.out.println("speedDelta="+speedDelta);
        }
        if (speed.x>0.01f && speed.x<2f){
            speed.scl(Math.abs(speedDelta));
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {//обработка нажатия курсоров
        switch (keycode){
            case (19): //вверх
                yRes++;
                break;
            case (20)://вниз
                yRes--;
                break;
            case (21)://влево
                xRes--;
                break;
            case (22)://вправо
                xRes++;
                break;
        }

        orientation();
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {//при нажатой кнопки мышки картинка двигается в след за курсором
        xRes= screenX;
        yRes=Gdx.graphics.getHeight() - screenY;
        orientation();
        return false;
    }

    private void orientation(){//для корректировки вектора направления движения
        if (xRes<pos.x) {
            speed.x=-1;
        }else if (xRes>pos.x){
            speed.x=1;
        }else{speed.x=0;}
        if (yRes<pos.y) {
            speed.y=-1;
        }else if (yRes>pos.y){
            speed.y=1;
        }else{speed.y=0;}

    }

}


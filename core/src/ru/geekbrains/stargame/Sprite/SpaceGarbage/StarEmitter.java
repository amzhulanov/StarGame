package ru.geekbrains.stargame.Sprite.SpaceGarbage;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import ru.geekbrains.stargame.math.Rect;

public class StarEmitter {
    private static TextureAtlas atlas;
    private static float delayTime = 0.5f; // задержка между звёздамиа
    private static float delayCounter = 0.0f; // счетчик звёзд
    private static Vector2 v = new Vector2();


    public static void initialize() {
        delayCounter = 0.5f;
        starsPool.clear();
    }

    private static final Pool<Star> starsPool = new Pool<Star>(128, 16) {
        //метод запускается, когда требуется создать новый экземпляр класса, а pool пустой
        @Override
        protected Star newObject() {
            atlas = new TextureAtlas("textures/menuAtlas.tpack");
            Star star = new Star(atlas);//создаётся новый экземпляр
            System.out.println("Создана новая звезда");

            return star;
        }
    };


    public static void clean(Array<Star> stars, int index) {
        Star star = stars.removeIndex(index);//удаляю звезду из списка
        starsPool.free(star);
        System.out.println("Звезда удалена в pool");

    }

    public static void run(Array<Star> stars, Rect worldBounds) {

        // если счетчик delaycounter превысил значение в delayTime
        if (delayCounter >= delayTime) {
            Star star = starsPool.obtain(); // получаем звезду из pool
            System.out.println("Звезда загружена из пула");
            star.resize(worldBounds);//переинициализирую звезду
            star.isActive=true;
            stars.add(star); // добавляем звезду

            delayCounter = 0.0f;// сбросить счетчик задержки
        } else {
            delayCounter += 0.1f;
            //в противном случае аккумулируем счетчик задержки
        }
    }


}

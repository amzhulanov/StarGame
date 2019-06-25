package ru.geekbrains.stargame.Sprite.SpaceGarbage;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import ru.geekbrains.stargame.math.Rect;

public class AsteroidEmitter {
    private static TextureAtlas atlas;
    private static float delayTime = 0.5f; // задержка между астероидами
    private static float delayCounter = 0.0f; // счетчик времени


    public static void initialize() {
        delayCounter = 0.5f;
        asteroidPool.clear();
    }

    private static final Pool<Asteroid> asteroidPool = new Pool<Asteroid>(2, 1) {
        //метод запускается, когда требуется создать новый экземпляр класса, а pool пустой
        @Override
        protected Asteroid newObject() {
            atlas = new TextureAtlas("textures/SpaceGarbage/pictures/spacegarbage.pack");
            Asteroid asteroid = new Asteroid(atlas);//создаётся новый экземпляр
            System.out.println("Создан новый астероид");

            return asteroid;
        }
    };


    public static void clean(Array<Asteroid> asteroids, int index) {
        Asteroid asteroid = asteroids.removeIndex(index);//удаляю астероид из списка
        asteroidPool.free(asteroid);
        System.out.println("Астероид удален в pool");

    }

    public static void run(Array<Asteroid> asteroids, Rect worldBounds) {

        // если счетчик delaycounter превысил значение в delayTime
        if (delayCounter >= delayTime) {
            Asteroid asteroid = asteroidPool.obtain(); // получаем астероид из pool
            System.out.println("Астероид загружен из пула");
            asteroid.resize(worldBounds);//переинициализирую звезду
            asteroid.isActive=true;
            asteroids.add(asteroid); // добавляем астероид

            delayCounter = 0.0f;// сбросить счетчик задержки
        } else {
            delayCounter += 0.01f;
            //в противном случае аккумулируем счетчик задержки
        }
    }


}

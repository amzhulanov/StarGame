package ru.geekbrains.stargame.Sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.stargame.Base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Shuttle extends Sprite {

    public Shuttle(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHalfHeight() / (float) 2);
        pos.set(worldBounds.pos);
    }
}

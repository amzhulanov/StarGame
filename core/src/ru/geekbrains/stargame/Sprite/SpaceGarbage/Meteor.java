package ru.geekbrains.stargame.Sprite.SpaceGarbage;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.Base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

import static ru.geekbrains.stargame.Base.ScaledTouchUpButton.INDENT;

public class Meteor extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    public Boolean isActive=true;

    public Meteor(TextureAtlas atlas) {
        super(atlas.findRegion("meteor-1"));
        v.set(Rnd.nextFloat(-0.01f, -0.005f), Rnd.nextFloat(-0.2f, -0.1f));
        setHeightProportion(0.025f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getLeft() < worldBounds.getLeft()) isActive=false;//звезда удаляется, только если ушла за левую сторону
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = worldBounds.getTop()-INDENT;//Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());

        pos.set(posX, posY);
    }
}

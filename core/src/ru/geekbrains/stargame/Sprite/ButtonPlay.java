package ru.geekbrains.stargame.Sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.Base.ScaledTouchUpButton;
import ru.geekbrains.stargame.Screen.GameScreen;
import ru.geekbrains.stargame.math.Rect;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas,Game game) {
        super(atlas.findRegion("btPlay"));
        this.game=game;
    }


    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setLeft(worldBounds.getLeft()+INDENT);
        setBottom(worldBounds.getBottom()+INDENT);
    }
}

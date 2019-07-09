package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.ScaledTouchUpButton;
import ru.geekbrains.stargame.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas,GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen=gameScreen;
        setHeightProportion(0.05f);
        setBottom(-0.02f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }

}

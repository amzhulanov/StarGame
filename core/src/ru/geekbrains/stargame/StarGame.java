package ru.geekbrains.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture imgBackground;
	Texture imgShuttle;
	TextureRegion regionTest;


	@Override
	public void create () {
		batch = new SpriteBatch();
		imgBackground = new Texture("space.jpg");
		imgShuttle =new Texture("shuttle.jpg");
		regionTest = new TextureRegion(imgShuttle,150,150,900,900);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0.56f, 0.44f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(imgBackground, 0, 0,800,600);
		batch.draw(imgShuttle, 10, 10,100,100);
		batch.draw(regionTest, 300, 10,90,90);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgBackground.dispose();
	}
}

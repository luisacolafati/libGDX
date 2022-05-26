package com.badlogic.drop;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Drop extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private SpriteBatch batch; //special class used to build 2D images
	
	//references to files in /assets
	private Texture waterDropImage;
	private Texture cactusImage;
	private Sound waterDropSound;
	private Music backgroundMusic;

	//libGDX class that allow us to save position and size of objects
	private Rectangle cactus;
	private Array<Rectangle> rainDrops;
	private long lastDropTime;//time when last rainDrop dropped, in nanoseconds

	@Override
	public void create () {
		//loading images from /assets, 64x64 pixels each
		waterDropImage = new Texture(Gdx.files.internal("waterDrop32.png"));
		cactusImage = new Texture(Gdx.files.internal("cactus.png"));
	
		//loading sounds from /assets
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("waterDrop.wav"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera(); //use a matrix for setting coordinate system for rendering
		camera.setToOrtho(false, 800, 480); //fixing screen game size
		batch = new SpriteBatch();

		//instantiate objects
		cactus = new Rectangle();
		cactus.x = (800 / 2) - (64 / 2);
		cactus.y = 20;
		cactus.width = 64; //pixels
		cactus.height = 64;//pixels

		rainDrops = new Array<Rectangle>();
		createRainDropInRandomPosition();
	}

	private void createRainDropInRandomPosition() {
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0, 800 - 64);
		rainDrop.y = 480;
		rainDrop.width = 32;
		rainDrop.height = 32;
		
		rainDrops.add(rainDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		//cleaning screen and setting a default background color, in RGB format
		ScreenUtils.clear(0.2f, 0.8f, 0.5f, 0);
		
		//tell the camera to update its space matrix
		camera.update();

		//rendering objects in specified screen position
		batch.setProjectionMatrix(camera.combined);//batch will use coordinate system defineed by camera
		batch.begin();
		batch.draw(cactusImage, cactus.x, cactus.y);
		for (Rectangle rainDrop: rainDrops) {
			batch.draw(waterDropImage, rainDrop.x, rainDrop.y);
		}
		batch.end();

		//move cactusImage with touch/mouse click
		if (Gdx.input.isTouched()) {//checking if had some touch/mouse click in screen
			Vector3 touchPosition = new Vector3();//3D vector, that save positions X, Y and Z from an object
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);//getting the current touch/mouse position
			camera.unproject(touchPosition);//converting touchPosition into camera coordinate system - used as game coordinate system
			cactus.x = touchPosition.x - (64 / 2);//center cactusImage in horizontal position pointed by mouse
		}

		//move cactusImage with left and right cursor of keyboard
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {//checking if a specified key in keyboard was pressed
			cactus.x -= 200 * Gdx.graphics.getDeltaTime();//getDeltaTime() return the time passed in seconds between the last and the current frame
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cactus.x += 200 * Gdx.graphics.getDeltaTime();
		}

		//forcing cactus to be inside screen horizontal limit (800 pixels)
		if (cactus.x < 0) {
			cactus.x = 0;
		}
		if (cactus.x > (800 - 64)) {
			cactus.x = 800 - 64;
		}

		//creating a new rainDrop every second
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			createRainDropInRandomPosition();
		}

		//making rainDrops moves vertically with constant velocity in screen
		for (Iterator<Rectangle> iter = rainDrops.iterator(); iter.hasNext(); ) {
			Rectangle rainDrop = iter.next();
			rainDrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if((rainDrop.y + 64 < 0)) {
				iter.remove();//removing rainDrops that reached bottom screen from rainDrops array
				this.dispose();
			}
			if (rainDrop.overlaps(cactus)) {
				waterDropSound.play();
				iter.remove();//removing rainDrops that reached cactus from rainDrops array
			}
		 }
	}
	
	@Override
	public void dispose () {
		//cleaning up game after it was closed
		//	use dispose() method for clean Textures, Sounds, Musics and SpriteBatches used
		waterDropImage.dispose();
		cactusImage.dispose();
		waterDropSound.dispose();
		backgroundMusic.dispose();
		batch.dispose();
	}
}

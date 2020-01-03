package com.spaceshooter.entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.spaceshooter.core.Collision;
import com.spaceshooter.core.Game;
import com.spaceshooter.input.KeyInput;
import com.spaceshooter.map.Id;
import com.spaceshooter.map.Layer;
import com.spaceshooter.math.Mathf;
import com.spaceshooter.math.Vector;
import com.spaceshooter.sprite.Animation;
import com.spaceshooter.utils.Assets;

public class Player extends Entity{

	Animation animation;
	KeyInput input;
	Vector lastPosition = new Vector();

	public Player(int x, int y, int width, int height, Id id, Layer layer) {
		super(x, y, width, height, id, layer);
		input = KeyInput.getInstance();
		texture.loadImage(Assets.SHIP, width, height);
		animation = new Animation(1, true, texture.imageArray);
		animation.goToFrame(0);
	}
	
	public void update() {
		
		lastPosition = position.clone();
		
		steeringForce.zero();
		
	    if (input.isKeyPressed(KeyEvent.VK_D)) steeringForce.setX(maxForce);
	    if (input.isKeyPressed(KeyEvent.VK_A)) steeringForce.setX(-maxForce);
	    if (input.isKeyPressed(KeyEvent.VK_S)) steeringForce.setY(maxForce);
	    if (input.isKeyPressed(KeyEvent.VK_W)) steeringForce.setY(-maxForce);
	    
		velocity = velocity.multiply(friction);
		velocity = velocity.add(steeringForce);
		velocity.truncate(maxSpeed);
		position = position.add(velocity);
		
		if(velocity.getDistSq() > 2)
			rotation = Math.toDegrees(velocity.getAngle());
		
		if(lastPosition.getX() != position.getX() || lastPosition.getY() != position.getY()) {
			Collision.detect(this, 0, true, new Collision.EventCallback() {

				@Override
				public void success(Entity source, Entity target) {
					Collision.resolve(source, target);
				}
			});
		}

		position.setX(Mathf.clamp(position.getX(), Game.MAP_X, Game.MAP_WIDTH
				+ Game.IMAGE_WIDTH - (Game.IMAGE_WIDTH + width)
				+ (Game.WINDOW_HEIGHT - Game.CANVAS_HEIGHT)));
		position.setY(Mathf.clamp(position.getY(), Game.MAP_Y, Game.MAP_HEIGHT
				+ Game.IMAGE_HEIGHT - (Game.IMAGE_HEIGHT + height)
				+ (Game.WINDOW_HEIGHT - Game.CANVAS_HEIGHT)));
	}
	
	public void render(Graphics2D g) {
		animation.rotateAnimation(g, rotation, (int) position.getX(), (int) position.getY(), width, height);
	}
}

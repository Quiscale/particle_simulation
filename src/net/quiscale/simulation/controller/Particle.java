package net.quiscale.simulation.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.quiscale.simulation.view.group.World;

public class Particle extends Rectangle {

	// /////////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////////

	public double x;
	public double y;
	public double vx;
	public double vy;
	public Color color;
	
	// /////////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////////

	public Particle(double x, double y, Color color) {
		super(3, 3);
		this.setFill(color);
		this.setX(x);
		this.setY(y);
		
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.color = color;
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////////

	public void move() {

		/*if(this.x <= 0 || this.x >= World.SCALE) {
			this.vx *= -1 ;
			this.x = this.x < 0 ? 0 : World.SCALE;
		}
		if(this.y <= 0 || this.y >= World.SCALE) {
			this.vy *= -1;
			this.y = this.y < 0 ? 0 : World.SCALE;
		}*/

		this.x += this.vx;
		this.y += this.vy;

		if(this.x < 0) {
			this.x += World.SCALE;
		}
		if(this.x > World.SCALE) {
			this.x -= World.SCALE;
		}
		if(this.y < 0) {
			this.y += World.SCALE;
		}
		if(this.y > World.SCALE) {
			this.y -= World.SCALE;
		}
		
		this.setX(this.x);
		this.setY(this.y);

	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Override
	// /////////////////////////////////////////////////////////////////////////////

}

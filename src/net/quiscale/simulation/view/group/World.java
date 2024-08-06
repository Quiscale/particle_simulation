package net.quiscale.simulation.view.group;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.quiscale.simulation.controller.Particle;

public class World extends Pane {

	// /////////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////////

	public static final double SCALE = 900;
	public static final double CENTER = SCALE /2;
	
	private Set<Particle> particles;
	private Particle[][] particleGroup;
	private int particleGroupNb;

	public DoubleProperty vitesseSimulation;
	public DoubleProperty viscosity;
	public DoubleProperty distance;
	public DoubleProperty[][] particleRules;
	
	// /////////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////////

	public World() {
		super();
		
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT)));
		this.setPrefSize(SCALE, SCALE);

		int particleByGroup = 500;
		
		this.particleGroupNb = 8;
		this.particles = new HashSet<>();
		// ~2000 avant d'avoir subis un semblant de lag
		this.particleGroup = new Particle[this.particleGroupNb][];
		this.particleGroup[0] = this.createGroup(particleByGroup, Color.YELLOW);
		this.particleGroup[1] = this.createGroup(particleByGroup, Color.RED);
		this.particleGroup[2] = this.createGroup(particleByGroup, Color.BLUE);
		this.particleGroup[3] = this.createGroup(particleByGroup, Color.GREEN);
		this.particleGroup[4] = this.createGroup(particleByGroup, Color.PURPLE);
		this.particleGroup[5] = this.createGroup(particleByGroup, Color.ORANGE);
		this.particleGroup[6] = this.createGroup(particleByGroup, Color.CYAN);
		this.particleGroup[7] = this.createGroup(particleByGroup, Color.MAGENTA);

		this.vitesseSimulation = new SimpleDoubleProperty(10);
		this.viscosity = new SimpleDoubleProperty(1.0);
		this.distance = new SimpleDoubleProperty(80);
		this.particleRules = new DoubleProperty[this.particleGroupNb][this.particleGroupNb];
		for(int i = 0; i < this.particleGroupNb; i++) {
			for(int j = 0; j < this.particleGroupNb; j++) {
				this.particleRules[i][j] = new SimpleDoubleProperty(0.0);
			}
		}
		
		this.getChildren().addAll(this.particles);
		
		new AnimationTimer() {
			
			long lastNow = 0;
			
			@Override
			public void handle(long now) {
				
				if(now > lastNow) {
					lastNow = now + 1000000 *vitesseSimulation.intValue();
					update();
				}
			}
		}.start();
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////////

	public void reset() {
		
		Random rand = new Random();
		
		this.particles.forEach( particle -> {
			particle.x = rand.nextDouble()*SCALE;
			particle.y = rand.nextDouble()*SCALE;
			particle.vx = 0;
			particle.vy = 0;
		});
		
	}
	
	public Particle[] createGroup(int number, Color color) {
		Random rand = new Random();
		Particle[] particles = new Particle[number];
		
		for(int i = 0; i < number; i++) {
			particles[i] = new Particle(rand.nextDouble()*SCALE, rand.nextDouble()*SCALE, color);
			this.particles.add(particles[i]);
		}
		
		return particles;
	}
	
	public void update() {

		for(int i = 0; i < this.particleGroupNb; i++) {
			for(int j = 0; j < this.particleGroupNb; j++) {
				this.rule(this.particleGroup[i], this.particleGroup[j], this.particleRules[i][j].get());
			}
		}
		
		
		this.particles.forEach(particle -> particle.move());
		
	}
	
	public void rule(Particle[] particlesA, Particle[] particlesB, double g) {
		
		for(Particle a : particlesA) {
			
			double fx = 0;
			double fy = 0;
	
			for(Particle b : particlesB) {

				double dx = a.x - b.x;
				double dy = a.y - b.y;
				double d = Math.sqrt(dx*dx + dy*dy);
				if(d < 2) {
					if(d > 0) {
						double F = 0.5 * 1/d;
						fx += (F *dx);
						fy += (F *dy);
					}
				} else {
					if(d < this.distance.get()) {
						double F = g * 1/d;
						fx += (F *dx);
						fy += (F *dy);
					}
				}
				
				
			} // end for
			
			a.vx = (a.vx +fx) *this.viscosity.get();
			a.vy = (a.vy +fy) *this.viscosity.get();
			
		} // end for
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Override
	// /////////////////////////////////////////////////////////////////////////////

}

package net.quiscale.simulation.view.group;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class RootGroup extends Group implements EventHandler<MouseEvent> {

	// /////////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////////

	private Node nodeDragged;
	private double nodeOrgX;
	private double nodeOrgY;
	private double dragOrgX;
	private double dragOrgY;
	
	// /////////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////////

	public RootGroup() {
		super();
		
		this.nodeDragged = null;
		this.nodeOrgX = 0;
		this.nodeOrgY = 0;
		
		World world = new World();
		Sliders sliders1 = Sliders.getWorldSettings(world);
		Sliders sliders2 = Sliders.getParticlesSettings(world);

		world.setTranslateX(250);
		world.setTranslateY(20);
		sliders1.setTranslateX(5);
		sliders1.setTranslateY(5);
		sliders2.setTranslateX(5);
		sliders2.setTranslateY(50);
		
		this.getChildren().addAll(world, sliders1, sliders2);

		this.getChildren().forEach( node -> node.setOnMousePressed(this) );
		this.setOnMouseDragged(this);
		this.setOnMouseReleased(this);
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////////

	private void select(Node node, double x, double y) {
		
		this.nodeDragged = node;
		this.nodeOrgX = node.getTranslateX();
		this.nodeOrgY = node.getTranslateY();
		this.dragOrgX = x;
		this.dragOrgY = y;
		
	}
	
	private void drag(double x, double y) {

		if(this.nodeDragged != null) {
			this.nodeDragged.setTranslateX(this.nodeOrgX +(x -this.dragOrgX));
			this.nodeDragged.setTranslateY(this.nodeOrgY +(y -this.dragOrgY));
		}
	}
	
	private void release() {
		
		this.nodeDragged = null;
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Override
	// /////////////////////////////////////////////////////////////////////////////

	@Override
	public void handle(MouseEvent event) {
		
		if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
			this.select( (Node) event.getSource(), event.getSceneX(), event.getSceneY() );
		
		if(event.getEventType() == MouseEvent.MOUSE_DRAGGED)
			this.drag(event.getSceneX(), event.getSceneY());
		
		if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
			this.release();
		
		
	}
	
}

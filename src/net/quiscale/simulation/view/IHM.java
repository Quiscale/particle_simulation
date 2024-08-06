package net.quiscale.simulation.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.quiscale.simulation.view.group.RootGroup;
import net.quiscale.simulation.view.group.World;

public class IHM extends Application {

	// /////////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////////

	public static final double WIDTH = World.SCALE;
	public static final double HEIGHT = World.SCALE;
	
	public static Stage STAGE = null;
	
	// /////////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////////
	
	public static void show(String[] args) {
		
		Application.launch(IHM.class, args);
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Override
	// /////////////////////////////////////////////////////////////////////////////

	@Override
	public void start(Stage stage) throws Exception {

		IHM.STAGE = stage;
		
		Scene scene = new Scene(new RootGroup(), WIDTH *1.5, HEIGHT*1.1);
		scene.setFill(Color.DARKGREY);
		
		stage.setScene(scene);
		stage.setTitle("Simulation");
		stage.show();
		
	}

}

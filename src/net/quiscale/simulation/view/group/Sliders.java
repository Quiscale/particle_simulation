package net.quiscale.simulation.view.group;

import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Sliders extends VBox {

	// /////////////////////////////////////////////////////////////////////////////
	// Attributes
	// /////////////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////////

	private Sliders() {
		super();

		CornerRadii cornerRadii = new CornerRadii(10);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, cornerRadii, BorderWidths.DEFAULT)));
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, cornerRadii, null)));
		this.setPadding(new Insets(10));
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////////////////////////

	private static Pane createNameScrollbar(String name, double minValue, double maxValue, DoubleProperty property) {

		HBox layout = new HBox();
		layout.setSpacing(10);
		
		Label label1 = new Label(name);
		label1.setPrefWidth(70);
		
		Label label2 = new Label();
		
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.HORIZONTAL);
		scroll.setMin(minValue);
		scroll.setMax(maxValue);
		scroll.valueProperty().addListener( (o, oV, nV) -> label2.setText(String.format("%.2f", nV.doubleValue())) );
		scroll.valueProperty().bindBidirectional(property);
		
		layout.getChildren().addAll(label1, scroll, label2);
		
		return layout;
	}
	
	public static Sliders getWorldSettings(World world) {
		
		Sliders sliders = new Sliders();

		sliders.getChildren().addAll(
				createNameScrollbar("Simulation", 1, 1000, world.vitesseSimulation),
				createNameScrollbar("Viscosité", 0, 1.05, world.viscosity),
				createNameScrollbar("Distance", 0, World.SCALE, world.distance)
		);
		
		return sliders;
	}
	
	public static Sliders getParticlesSettings(World world) {
		
		Sliders sliders = new Sliders();
		
		char[] ls = new char[] {'Y', 'R', 'B', 'G', 'P', 'O', 'C', 'M'}; // ls for letters
		for(int i = 0; i < ls.length; i++) {
			for(int j = 0; j < ls.length; j++)
				sliders.getChildren().add(createNameScrollbar(ls[i] + " <-> " + ls[j], -1, 1, world.particleRules[i][j]));
			sliders.getChildren().add(new Label());
		}
		
		Button buttonRandom = new Button("Aléatoire");
		buttonRandom.setOnAction( event -> {
			Random rand = new Random();
			for(int i = 0; i < ls.length; i++) {
				for(int j = 0; j < ls.length; j++)
					world.particleRules[i][j].set(rand.nextDouble() *2 -1);
			}
		});
		Button buttonReset = new Button("Mélanger");
		buttonReset.setOnAction( event -> {
			world.reset();
		});
		
		sliders.getChildren().addAll(buttonRandom, buttonReset);
		
		return sliders;
		
	}
	
	// /////////////////////////////////////////////////////////////////////////////
	// Override
	// /////////////////////////////////////////////////////////////////////////////

}

package model.cliping;

import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class Recorte {
	
	
	public static void recorteQuadrado(Region regiao, double width, double height, double arcoWidth, double arcoHeight) {

	    Rectangle outputClip = new Rectangle();
	    outputClip.setArcWidth(arcoWidth);
	    outputClip.setArcHeight(arcoHeight);
	    outputClip.setWidth(width);
	    outputClip.setHeight(height);
	    regiao.setClip(outputClip);

	    regiao.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
	        outputClip.setWidth(newValue.getWidth());
	        outputClip.setHeight(newValue.getHeight());
	    });        
	}
	
	public static void recorteCirculo(Region regiao, double width, double height, double arcoWidth, double arcoHeight) {

	           
	}

}

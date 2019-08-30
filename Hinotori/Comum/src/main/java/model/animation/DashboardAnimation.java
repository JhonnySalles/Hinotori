package model.animation;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashboardAnimation {
	static DoubleTransition dt;
	
	public static void abrirPaneBotao(SplitPane sp, AnchorPane ap) {	
		if (dt != null)
			dt.stop();
		
		ap.setMaxWidth(150);
		DoubleProperty dprop = sp.getDividers().get(0).positionProperty();
		dt = new DoubleTransition(Duration.millis(600), dprop);
		dt.setToValue(1);
		dt.setDelay(Duration.seconds(2));
		dt.setOnFinished(t -> {ap.setMinWidth(150);});
		dt.play();	
		SplitPane.setResizableWithParent(sp, Boolean.FALSE);
	}
	
	public static void fecharPaneBotao(SplitPane sp, AnchorPane ap) {
		if (dt != null)
			dt.stop();
		
		ap.setMinWidth(47);
		DoubleProperty dprop = sp.getDividers().get(0).positionProperty();
		dt = new DoubleTransition(Duration.millis(600), dprop);
		dt.setToValue(0); 
		dt.setOnFinished(t -> {ap.setMaxWidth(47);});
		dt.setDelay(Duration.seconds(1));
		dt.play();
		SplitPane.setResizableWithParent(sp, Boolean.FALSE);
	}
}

package application;

import javafx.scene.layout.HBox;

public class CommonObjects {
	
	private static CommonObjects commonObjects = new CommonObjects();
	
	private HBox mainBox;
	
	private CommonObjects() {}
	
	public static CommonObjects getInstance() {
		return commonObjects;
	}
	
	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}

	
	
	
	

}

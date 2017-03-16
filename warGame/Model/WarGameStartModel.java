package warGame.Model;

import java.util.Observable;

public class WarGameStartModel extends Observable{
	/**
	 * temp show map view
	 */
	public void DisplayMapView(){
		setChanged();
		notifyObservers(this);
	}
}

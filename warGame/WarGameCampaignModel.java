package warGame;

import java.util.Observable;

class WarGameCampaignModel extends Observable{

	public void createCampaign(String newCampaignSe){
		int length = newCampaignSe.trim().length();
		campaignSe = new String[length];
		campaignSe = newCampaignSe.split(",");
		setChanged();
		notifyObservers(this);
	}
	
/*************************************added************************************/
	private
	String campaignSe[];
	
	public String[] getCampaignSe(){
		return campaignSe;
	}
}

package warGame.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WarGameCampaignModel extends Observable{
//--------------------------------------------------Attributes--------------------------------------------------	
	/**
	 * unique campaignID
	 */
	private String campaignID;
	/**
	 * campaign name
	 */
	private String campaignName;
	/**
	 * contains map
	 */
	private ArrayList<String> mapLists;

	
//--------------------------------------------------Methods--------------------------------------------------	
	
	/**
	 * default construct
	 */
	public WarGameCampaignModel(){}
	/**
	 * custom construct
	 * @param campaignName
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	public WarGameCampaignModel(String campaignName) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		this.campaignID = Integer.parseInt(this.lastCampaignID())+1+"";
		this.campaignName = campaignName;
	}
	
	/**
	 * used in controller to call the viewer
	 */
	public void setCampaignCreationView(){
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * used in controller to call the viewer
	 */
	public void setCampaignLoadView(){
		setChanged();
		notifyObservers(this);
	}
	
	//add
	public ArrayList<Integer> BFS(){
		//boolean[] isSearched = new boolean[vertices];
		for (int i = 0; i < vertices; i++) {
			if (!checked.contains(i)){
				int count = 0;
				Queue<Integer> q = new LinkedList<Integer>();
				q.add(i);
				while(!q.isEmpty()){
					Integer currentV = q.remove();
					checked.add(currentV);
					for (int k = currentV+1; k < vertices; k++) {
						if(bool_adj[currentV][k] && !checked.contains(k)){
							q.add(k);
							checked.add(k);
						}
					}
					for (int j = 0; j < currentV; j++) {
						if(bool_adj[j][currentV] && !checked.contains(j)){
							q.add(j);
							checked.add(j);
						}
					}
					//q.remove();
					count++;
				}
				subgraphsize.add(count);
			}	
		}
		return this.subgraphsize;
	}
	
	/**
	 * @return the last key of the campaign in the json file
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public String lastCampaignID() throws UnsupportedEncodingException, FileNotFoundException{
		ArrayList<String> allKeysInMap = new ArrayList<String>();
		Map<String, WarGameCampaignModel> campaignsByMap = WarGameCampaignModel.listAllCampaigns();
		Iterator<Entry<String, WarGameCampaignModel>> it = campaignsByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WarGameCampaignModel> entry = (Map.Entry<String, WarGameCampaignModel>)it.next();
			allKeysInMap.add(entry.getKey());
		}
		String lastCampaignID = allKeysInMap.get(allKeysInMap.size()-1);
		return lastCampaignID;
	}
	
	/**
	 * list all the campaigns from the map file
	 * @return a campaign
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static Map<String, WarGameCampaignModel> listAllCampaigns() throws UnsupportedEncodingException, FileNotFoundException{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("src/file/campaigns.json"), "UTF-8");
		Map<String, WarGameCampaignModel> campaignsByMap = gson.fromJson(isreader, new TypeToken<Map<String, WarGameCampaignModel>>(){}.getType());
		return campaignsByMap;
	}
	
	/**
	 * save the campaign into the campaign file
	 * @param campaignModel
	 * @throws IOException 
	 */
	public void saveCampaign(WarGameCampaignModel campaignModel) throws IOException{
		Map<String, WarGameCampaignModel> campaignsByMap = WarGameCampaignModel.listAllCampaigns();
		campaignsByMap.put(campaignModel.getCampaignID(), campaignModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/campaigns.json");
		fw.write(gson.toJson(campaignsByMap));
		fw.close();
	}
	
	/**
	 * load the specific campaign by campaignID
	 * @param campaignID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public WarGameCampaignModel loadCampaign(String campaignID) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, WarGameCampaignModel> campaignsByMap = WarGameCampaignModel.listAllCampaigns();
		WarGameCampaignModel campaignModel = campaignsByMap.get(campaignID);
		return campaignModel;
	}
	
//--------------------------------------------------Getters/Setters--------------------------------------------------	
	/**
	 * get the campaign ID
	 * @return
	 */
	public String getCampaignID() {
		return campaignID;
	}
	
	/**
	 * set the campaign ID
	 * @param campaignID
	 */
	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
	}
	
	/**
	 * get the name of the campaign
	 * @return
	 */
	public String getCampaignName() {
		return campaignName;
	}
	
	/**
	 * set the name of the campaign
	 * @param campaignName
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	/**
	 * get the map name arraylist of the campaign
	 * @return
	 */
	public ArrayList<String> getMapLists() {
		return mapLists;
	}
	
	/**
	 * set the map name arraylist of the campaign
	 * @param mapLists
	 */
	public void setMapLists(ArrayList<String> mapLists) {
		this.mapLists = mapLists;
	}
	
	/**
	 * override the toString method
	 */
	@Override
	public String toString() {
		return this.campaignName;
	}
}

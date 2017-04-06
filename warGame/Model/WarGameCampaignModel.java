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
	public Event(String input){
		/*parseInput(input);
	}
	
	private void parseInput(String input){*/
		char[] inputStream = input.trim().toCharArray();
		int count = 0;
		String buff = "";
		boolean isX = true;
		for(char c : inputStream){
			if(c >= 48 && c <=57)
				buff += c;
			else if(buff != ""){
				Integer temp = Integer.parseInt(buff);
				if(count == 0){
					this.total = temp;
					count++;
				}
				else if(count == 1){
					this.desired = temp;
					count++;
				}
				else{
					if(isX)
						this.x.add(temp);
					else
						this.y.add(temp);
				}
				if(c == '}')
					isX = false;
				buff = "";
			}
		}
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
	
	//add
	private void drawScore(Graphics g)
    {
        int i = lV + 1;
        int j = mS;
        if(j > 0x1869f)
            j = 0x1869f;
        if(mS > mH)
        {
            mH = mS;
            setGConf(mH, sE);
            saveData();
        }
        int k = mH;
        if(k > 0x1869f)
            k = 0x1869f;
        try
        {
            char ac[] = Integer.toString(i).toCharArray();
            int l = ac.length;
            for(int i1 = 0; i1 < l; i1++)
                g.drawImage(numI[Character.digit(ac[i1], 10)], 132 + (5 - (l - i1)) * 8, 36, 0x10 | 0x4);

            ac = Integer.toString(j).toCharArray();
            l = ac.length;
            for(int j1 = 0; j1 < l; j1++)
                g.drawImage(numI[Character.digit(ac[j1], 10)], 132 + (5 - (l - j1)) * 8, 84, 0x10 | 0x4);

            ac = Integer.toString(k).toCharArray();
            l = ac.length;
            for(int k1 = 0; k1 < l; k1++)
                g.drawImage(numI[Character.digit(ac[k1], 10)], 132 + (5 - (l - k1)) * 8, 132, 0x10 | 0x4);

        }
        catch(Exception exception) { }
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

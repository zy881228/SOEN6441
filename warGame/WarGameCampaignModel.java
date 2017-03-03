package warGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;


/**
 * This class is a campaign model contains methods that allows user to :
 * <p>Create a new campaign<br/>
 * <p>Set the order of the campaign<br/>
 * <p>Edit the order of the campaign<br/>
 * @version build 1
 */

class WarGameCampaignModel extends Observable{

	private ArrayList<String> mapFileName = new ArrayList<String>();
	private String errorMsg = new String();
	
    
    /**
     * Create a new campaign.
     * <p> The sequence of the campaign.<br/>
     */
	public void createCampaign(){
		campaignList.clear();
		viewType = 1;
		setChanged();
		notifyObservers(this);
	}
    
    /**
     * <p>save the campaign<br/>
     * @param newCampaignID the ID number of the new campaign
     * @param newCampaignSeq the sequence of the new campaign
     * @throws IOException
     * @return
     */
    
	public Boolean saveCampaign(int newCampaignID,ArrayList<String> newCampaignSeq) throws IOException{
		String message = new String();
		for (String str : newCampaignSeq) {
			message = message+" "+str;
		}
		message = newCampaignID+message+"\r\n";
		File logFile = new File("src/file/campaign.txt");
		FileOutputStream os;
	    os = new FileOutputStream(logFile,true);
        os.write(message.getBytes());
	    os.close();
		return true;
	}
    
    /**
     * <p>load the campaign<br/>
     * @param newCampaignID the ID number of the new campaign
     * @throws IOException
     * @return
     */
    
	public void loadCampaign(String newCampaignID) throws IOException{
		campaignList.clear();
		String buffer = new String();
		String campaignID_buffer[] = newCampaignID.trim().split("n");
		File file = new File("src/file/campaign.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			String str[] = buffer.trim().split(" ");
			if(str[0].equals(campaignID_buffer[1]))
			{
				campaignID = str[0];
				for(int i=1;i<str.length;i++)
				{
					campaignList.add(str[i]);
				}
			}
			
		}
		viewType = 2;
		setChanged();
		notifyObservers(this);
	}
    
    /**
     * <p>load the campaign list<br/>
     */

	public void loadCampaignList(){
		viewType = 3;
		setChanged();
		notifyObservers(this);
	}
    
    /**
     * <p>edit the campaign<br/>
     * @param newID the ID number of the new campaign
     * @param newCampaignList the list of the new campaign
     * @throws IOException
     * @return
     */
    
	public Boolean editCampaign(String newID,ArrayList<String> newCampaignList) throws IOException{
		String message = new String();
		String buffer = new String();
		ArrayList <String> messageList = new ArrayList();
		File file = new File("src/file/campaign.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			String str[] = buffer.trim().split(" ");
			if(str[0].equals(newID))
			{
				for (String str_buffer : newCampaignList) {
					message = message + str_buffer+" ";
				}
				message = newID + " "+message+"\r\n";
				messageList.add(message);
			}
			else
			{
				message = new String();
				for(int i=0;i<str.length;i++)
				{
					message = message + str[i]+" ";
				}
				message = message + "\r\n";
				messageList.add(message);
			}
		}
		file.delete();
		file = new File("src/file/campaign.txt");
		FileOutputStream os;
		os = new FileOutputStream(file,true);
		for(int i=0;i<messageList.size();i++)
		{
			os.write(messageList.get(i).getBytes());
		}
		os.close();
		return true;
	}
    
    
    /**
     * <p>get all the campaign<br/>
     * @throws IOException
     * @return
     */
    
	public int getTotalNum() throws IOException{
		String buffer = new String();
		int count = 0;
		File file = new File("src/file/campaign.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			count++;
		}
		return count;
	}
    
    
    /**
     * <p>list all the campaign<br/>
     */
    
	public void listing(){
		this.mapFileName = new ArrayList<String>();
		String path = "src/file/map/";
		File file = new File(path);
		File[] fileArray = file.listFiles();
		for(int i=0;i<fileArray.length;i++){ 
			if(fileArray[i].isFile()){
				this.mapFileName.add(fileArray[i].getName());
			}else{
				this.errorMsg = "No map files.";
			}
		}
//		for (String tempMapFileName : this.mapFileName) {
//			System.out.println(tempMapFileName);
//		}		
//		viewModel = 4;
//		setChanged();
//		notifyObservers(this);
	}
	
	public ArrayList<String> getMapFileName (){
		return this.mapFileName;
	}
/*************************************added************************************/
	private
    
    /**
     *<p> campaignSe is the sequence of the campaign.<br/>
     */
    
	String campaignSe[];
    
    /**
     *<p> campaignID is the ID number of the campaign.<br/>
     */
	String campaignID;
    
    /**
     *<p> viewType is the type of the view of diifferent operations.<br/>
     */
	int viewType = 0;
    
    /**
     *<p> create a new arraylist to store the campaign list.<br/>
     */
	ArrayList<String> campaignList = new ArrayList();
	
    /**
     *<p> get the campaign sequence<br/>
     */
    
	public String[] getCampaignSe(){
		return campaignSe;
	}
    
    /**
     *<p> get the campaign ID number<br/>
     */
    
	public String getCampaignID(){
		return campaignID;
	}
    
    /**
     *<p> get the view of the operation<br/>
     */
    
    public int getViewType(){
		return viewType;
	}
    
    /**
     *<p> get the campaign list<br/>
     */
    
	public ArrayList<String> getCampaignList(){
		return campaignList;
	}
}

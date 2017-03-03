package warGame;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class CampaignModelTest1 extends TestCase{

	WarGameCampaignModel campaignModel = new WarGameCampaignModel();
	
	
	public void setUp() throws Exception {
		System.out.println("Campaign Test1 begins");
		campaignModel.loadCampaign("Camapaign1");
	}
	
	public void tearDown() throws Exception {
		System.out.println("Campaign Test1 ends");
		System.out.println();
	}
	
	public void testEditCampaign() throws IOException{
		System.out.println("Test edit campaign");
		String changeBefore = new String();
		String changeAfter = new String();
		String itemID = campaignModel.getCampaignID();
		String newCampaign = "Map5";
		ArrayList<String> campaignList = campaignModel.getCampaignList();
		ArrayList<String> campaignList_reLoad = new ArrayList();
		campaignList.add(newCampaign);
		campaignModel.editCampaign(itemID,campaignList);
		campaignModel.loadCampaign("Campaign"+itemID);
		campaignList_reLoad = campaignModel.getCampaignList();
		assertEquals(campaignList, campaignList_reLoad);
		
	}
}

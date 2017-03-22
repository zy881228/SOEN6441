package warGame.View;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import warGame.Model.*;

/**
 * This viewer allow users to manipulate the start choosing information in GUI
 * 
 */
@SuppressWarnings("serial")
public class WarGameChooseView extends JFrame implements Observer{
	private Map<String, WarGameCharacterModel> charactersByMap;
	private Map<String, WarGameCampaignModel> campaignsByMap;
	private WarGameStartModel startModelOnPage;
	
	/**
     * Update the choosing information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */
	@Override
	public void update(final Observable o, Object arg) {
		
		try {
			charactersByMap = WarGameCharacterModel.listAllCharacters();
			campaignsByMap = WarGameCampaignModel.listAllCampaigns();
			startModelOnPage = null;
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

		
		final JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle("WarGame");
		frame.setSize(600, 400);
		frame.getContentPane().setLayout(null);
		
		JLabel lblChooseCharacter = new JLabel("Character");
		lblChooseCharacter.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseCharacter.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblChooseCharacter.setBounds(60, 100, 150, 21);
		frame.getContentPane().add(lblChooseCharacter);
		
		JLabel lblChooseCampaign = new JLabel("Campaign");
		lblChooseCampaign.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseCampaign.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblChooseCampaign.setBounds(60, 160, 150, 21);
		frame.getContentPane().add(lblChooseCampaign);
		
		final JComboBox<WarGameCharacterModel> characterCbox = new JComboBox<WarGameCharacterModel>();
		characterCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		characterCbox.setBounds(220, 102, 120, 21);
		frame.getContentPane().add(characterCbox);
		for (String characterID : charactersByMap.keySet()) {
			characterCbox.addItem(charactersByMap.get(characterID));
		}
		
		final JComboBox<WarGameCampaignModel> campaignCbox = new JComboBox<WarGameCampaignModel>();
		campaignCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		campaignCbox.setBounds(220, 162, 120, 21);
		frame.getContentPane().add(campaignCbox);
		for (String campaignID : campaignsByMap.keySet()) {
			campaignCbox.addItem(campaignsByMap.get(campaignID));
		}
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnStart.setBounds(220,250,120,30);
		frame.getContentPane().add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					startModelOnPage = new WarGameStartModel((WarGameCharacterModel)characterCbox.getSelectedItem(), (WarGameCampaignModel)campaignCbox.getSelectedItem());
					WarGameStartView startView= new WarGameStartView();
					startModelOnPage.addObserver(startView);
					startModelOnPage.DisplayMapView();
					frame.dispose();
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}

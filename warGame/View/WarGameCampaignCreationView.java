package warGame.View;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import warGame.Model.*;

@SuppressWarnings("serial")
public class WarGameCampaignCreationView extends JFrame implements Observer{
	private JTextField campaignNameText;
	private Map<String, WarGameCampaignModel> campaignsByMap;
	private WarGameCampaignModel campaignOnPage;
	private Map<String, WarGameMapModel> mapsByMap;
	private ArrayList<JLabel> mapsLbls;

	@Override
	public void update(Observable o, Object arg) {
		try {
			campaignsByMap = WarGameCampaignModel.listAllCampaigns();
			mapsByMap = WarGameMapModel.listAllMaps();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setTitle("Campaigns");
		frame.setSize(500, 600);
		mapsLbls = new ArrayList<JLabel>();
		campaignOnPage = new WarGameCampaignModel();
		final ArrayList<String> mapLists = new ArrayList<String>();
		
		final JPanel manipulatePanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.setBounds(0, 40, 500, 600);
		frame.getContentPane().add(manipulatePanel);
		manipulatePanel.setLayout(null);
		
		JLabel lblCampaignName = new JLabel("Campaign name:");
		lblCampaignName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblCampaignName.setBounds(30, 70, 120, 30);
		manipulatePanel.add(lblCampaignName);
		
		campaignNameText = new JTextField();
		campaignNameText.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		campaignNameText.setBounds(150, 70, 100, 30);
		manipulatePanel.add(campaignNameText);
		campaignNameText.setColumns(10);
		
		final JPanel mapListPanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mapListPanel.setBounds(30, 160, 400, 300);
		manipulatePanel.add(mapListPanel);
		mapListPanel.setLayout(null);
		
		final JButton btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		btnAdd.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnAdd.setBounds(300, 120, 120, 30);
		manipulatePanel.add(btnAdd);
		
		final JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnCreate.setBounds(300, 70, 120, 30);
		manipulatePanel.add(btnCreate);
		
		final JComboBox<WarGameMapModel> mapToChooseCbox = new JComboBox<>();
		mapToChooseCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mapToChooseCbox.setBounds(150, 120, 100, 30);
		manipulatePanel.add(mapToChooseCbox);
		for (String mapID : mapsByMap.keySet()) {
			mapToChooseCbox.addItem(mapsByMap.get(mapID));
		}
		
		final JButton btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnSave.setBounds(180, 470, 120, 30);
		manipulatePanel.add(btnSave);
		
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<WarGameCampaignModel> allKeysInMap = new ArrayList<WarGameCampaignModel>();
				Iterator<Entry<String, WarGameCampaignModel>> it = campaignsByMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, WarGameCampaignModel> entry = (Map.Entry<String, WarGameCampaignModel>)it.next();
					allKeysInMap.add(entry.getValue());
				}
				for (WarGameCampaignModel alreadyExist : allKeysInMap) {
					if(alreadyExist.getCampaignName().equals(campaignNameText.getText())){
						JOptionPane.showMessageDialog(null, "Campaign name already exist");
						campaignNameText.setText("Invalid campaign name");
					}
				}
				if (campaignNameText.getText().equals("Invalid campaign name")||campaignNameText.getText().equals("")||campaignNameText.getText().contains(" ")) {
					JOptionPane.showMessageDialog(null, "Invalid campaign name");
					campaignNameText.setText("");
				}else{
					try {
						campaignOnPage = new WarGameCampaignModel(campaignNameText.getText());
					} catch (NumberFormatException | UnsupportedEncodingException | FileNotFoundException e1) {
					}
				campaignNameText.setEditable(false);
				btnAdd.setEnabled(true);
				btnCreate.setEnabled(false);
			}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mapLists.size()>=20) {
					JOptionPane.showMessageDialog(null, "No more than 20 maps");
				}else{
					JLabel mapsLbl = new JLabel();
					mapsLbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
					mapsLbl.setText(mapToChooseCbox.getSelectedItem().toString());
					mapsLbls.add(mapsLbl);
					mapLists.add(mapToChooseCbox.getSelectedItem().toString());
					for (int i = 0; i < mapsLbls.size(); i++) {
						if (i<8) {
							mapsLbls.get(i).setBounds(30, i*30, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else if(i>=8&&i<16){
							mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else if(i>=16&&i<20){
							mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}
					}
					mapListPanel.repaint();
					btnSave.setEnabled(true);
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					campaignOnPage.setMapLists(mapLists);
					campaignOnPage.saveCampaign(campaignOnPage);
					btnSave.setEnabled(false);
					campaignNameText.setEditable(true);
					campaignNameText.setText("");
					btnCreate.setEnabled(true);
					btnAdd.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	
		JLabel lblMapName = new JLabel("Map name:");
		lblMapName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblMapName.setBounds(30, 120, 120, 30);
		manipulatePanel.add(lblMapName);
	
	}
}

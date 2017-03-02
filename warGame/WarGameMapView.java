import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This viewer allow users to manipulate the map information in GUI
 * <p>Case 1: show the map<br/>
 * <p>Case 2: Save the map<br/>
 * <p>Case 3: Edit the map<br/>
 * <p>Case 4: Load the map<br/>
 * <p>Case 5: Create the map<br/>
 * <p>Case 6: Show errors message before saving<br/>
 * @version build 1
 * @author Student ID: 40001993
 */
@SuppressWarnings("serial")
class WarGameMapView extends JFrame implements Observer{
	
	@Override
	public void update(final Observable o, Object arg) {
		final String mapName = ((WarGameMapModel) o).getMapName();
		final String map[][] = ((WarGameMapModel) o).getMap();
		int viewModel = ((WarGameMapModel) o).getViewModel();
		ImageIcon wall = new ImageIcon("src/image/Map/wall.jpg");
		ImageIcon door = new ImageIcon("src/image/Map/door.jpg");
		ImageIcon chest = new ImageIcon("src/image/Map/chest.jpg");
		ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
		ImageIcon monster = new ImageIcon("src/image/Map/monster.jpg");
		ImageIcon hero = new ImageIcon("src/image/Map/hero.jpg");
		
		switch (viewModel) {
		
		//show map
		case 1:
			final JFrame frame = new JFrame("Map: "+ mapName);
			frame.setBounds(300, 200, 1000, 800);
			this.setLocation(0,0);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.addKeyListener(new KeyListener() {				
				@Override
				public void keyTyped(KeyEvent e) {					
				}				
				@Override
				public void keyReleased(KeyEvent e) {					
				}			
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						frame.setVisible(false);
					}
				}
			});
			frame.setVisible(true);
			frame.setLayout(null);
			JPanel mapPanel = new JPanel();
			GridBagLayout gbl = new GridBagLayout();
			mapPanel.setLayout(gbl);
			GridBagConstraints c = new GridBagConstraints();
//			c.insets = new Insets(20, 20, 20, 20);
//			c.ipadx = 8;
//			c.ipady = 8;
			
//			for (int i = 0; i < map.length; i++) {
//				for (int j = 0; j < map[i].length; j++) {
//					JLabel mapElement = new JLabel(map[i][j]);
//					c.gridx = j;
//					c.gridy = i;
//					mapPanel.add(mapElement,c);
//				}
//			}
			
			//Show images
			for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				JLabel mapElement = new JLabel(map[i][j]);
				switch (map[i][j]) {
				case "x":
					mapElement.setIcon(wall);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "i":
					mapElement.setIcon(chest);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "h":
					mapElement.setIcon(hero);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "m":
					mapElement.setIcon(monster);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "I":
					mapElement.setIcon(door);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "O":
					mapElement.setIcon(door);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "f":
					mapElement.setIcon(floor);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				case "":
					mapElement.setIcon(floor);
					mapElement.setText("");
					c.gridx = j;
					c.gridy = i;
					mapPanel.add(mapElement,c);
					break;
				}
			}
		}			
			frame.add(mapPanel);
			frame.setContentPane(mapPanel);
			break;

		//save map
		case 2:
			JOptionPane.showMessageDialog(null, mapName+" saved successfully.");	
			break;
			
		//edit map
		case 3:
			
			break;
			
		//load map
		case 4:
//			String selectedFileName;
			final JFrame loadFrame = new JFrame("Loading a map");
			loadFrame.setBounds(300, 200, 800, 600);
			loadFrame.setSize(700,500);
			this.setLocation(0,0);
			loadFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
			final JPanel loadPanel = new JPanel();
			GridBagLayout loadGbl = new GridBagLayout();
			loadPanel.setLayout(loadGbl);
			GridBagConstraints loadC = new GridBagConstraints();
			loadC.fill = GridBagConstraints.HORIZONTAL;
			 
			ArrayList<String> files = ((WarGameMapModel) o).getMapFileName();
			if (files.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No map files!");
			}
			
			JLabel selectFile = new JLabel("Choose a map");
			loadC.gridx = 1;
			loadC.gridy = 0;
			loadPanel.add(selectFile, loadC);
			
			final JComboBox<String> fileCbox = new JComboBox<String>();
			for (String fileName : files) {				
				fileCbox.addItem(fileName);
			}
			loadC.gridx = 2;
			loadC.gridy = 0;
			loadPanel.add(fileCbox, loadC);

			
			final JButton saveInLoad = new JButton("Save");
			saveInLoad.setEnabled(false);
			loadC.gridx = 4;
			loadC.gridy = 4;
			loadPanel.add(saveInLoad, loadC);
			final JButton editButton = new JButton("Edit");
			editButton.setEnabled(false);
			loadC.gridx = 3;
			loadC.gridy = 4;
			loadPanel.add(editButton, loadC);

			JLabel edit = new JLabel("Edit");
			loadC.gridx = 1;
			loadC.gridy = 3;
			loadPanel.add(edit,loadC);
			
//			JLabel labelEdit = new JLabel("(position x, position y, element)");
//			loadC.gridx = 1;
//			loadC.gridy = 4;
//			loadPanel.add(labelEdit,loadC);
			
//			final JTextField positionsXinLoad = new JTextField(10);
			final JComboBox<Integer> positionXinLoadCbox = new JComboBox<Integer>();
			positionXinLoadCbox.setEnabled(false);
			positionXinLoadCbox.setEditable(false);
//			positionsXinLoad.setEditable(false);
			loadC.gridx = 2;
			loadC.gridy = 3;
//			loadPanel.add(positionsXinLoad,loadC);
			loadPanel.add(positionXinLoadCbox,loadC);
			
//			final JTextField positionsYinLoad = new JTextField(10);
			final JComboBox<Integer> positionYinLoadCbox = new JComboBox<Integer>();
//			positionsYinLoad.setEditable(false);
			positionYinLoadCbox.setEnabled(false);
			positionYinLoadCbox.setEditable(false);
			loadC.gridx = 3;
			loadC.gridy = 3;
//			loadPanel.add(positionsYinLoad,loadC);
			loadPanel.add(positionYinLoadCbox,loadC);
			
//			String tempElementinLoad[] = {"x", "i","h","m","I","O"};
//			final JTextField elementinLoad = new JTextField(10);
			final JComboBox<String> elementinLoadCbox = new JComboBox<String>();
//			elementinLoad.setEditable(false);
			elementinLoadCbox.setEnabled(false);
//			for (String string : tempElementinLoad) {
//				elementinLoadCbox.addItem(string);
//			}
			loadC.gridx = 4;
			loadC.gridy = 3;
//			loadPanel.add(elementinLoad,loadC);
			loadPanel.add(elementinLoadCbox,loadC);
			
			final JButton showMapButton = new JButton("Load");
			showMapButton.setEnabled(false);
			loadC.gridx = 4;
			loadC.gridy = 0;
			loadPanel.add(showMapButton, loadC);
			
			fileCbox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showMapButton.setEnabled(true);
				}
			});
			
			showMapButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					positionXinLoadCbox.removeAllItems();
					positionYinLoadCbox.removeAllItems();
					elementinLoadCbox.removeAllItems();
					try {
						((WarGameMapModel) o).showMapByName(fileCbox.getSelectedItem().toString());
						String tempMap[][] = ((WarGameMapModel) o).getMap();
						for (int i = 1; i < tempMap.length+1; i++) {
							positionXinLoadCbox.addItem(i);
						}
						for (int i = 1; i < tempMap[0].length+1; i++) {
							positionYinLoadCbox.addItem(i);
						}
					} catch (IOException e1) {
//						e1.printStackTrace();
					}
					editButton.setEnabled(true);
//					positionsXinLoad.setEditable(true);
					positionXinLoadCbox.setEnabled(true);
//					positionXinLoadCbox.setEditable(true);
//					positionsYinLoad.setEditable(true);
					positionYinLoadCbox.setEnabled(true);
//					positionYinLoadCbox.setEditable(true);
//					elementinLoad.setEditable(true);
					elementinLoadCbox.setEnabled(true);
					ArrayList<String> characters = new ArrayList<String>();
					ArrayList<String> items = new ArrayList<String>();
					try {
						((WarGameMapModel) o).listCharacter();
						((WarGameMapModel) o).listItems();
					} catch (IOException e1) {
//						e1.printStackTrace();
					}
					if (characters!=null) {					
						characters = ((WarGameMapModel) o).getCharacterName();
						
						for (String s : characters) {
							elementinLoadCbox.addItem("character"+s);
							elementinLoadCbox.addItem("monster"+s);
						}
					}
					
					if (items!=null) {
						items = ((WarGameMapModel) o).getItemName();
						
						for (String s : items) {
							elementinLoadCbox.addItem("item"+s);
						}
					}
					elementinLoadCbox.addItem("Entry door");
					elementinLoadCbox.addItem("Exit door");
					elementinLoadCbox.addItem("Wall");
					elementinLoadCbox.addItem("Remove");
					
				}
			});
			
			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					((WarGameMapModel) o).setElements(Integer.parseInt(positionsXinLoad.getText()), Integer.parseInt(positionsYinLoad.getText()), elementinLoad.getText());
					String tempS;
					tempS = elementinLoadCbox.getSelectedItem().toString();
					if (tempS.startsWith("character")) {
						tempS = "h";
					}else if(tempS.startsWith("monster")){
						tempS = "m";
					}else if(tempS.startsWith("item")){
						tempS = "i";
					}else if(tempS.startsWith("Entry")){
						tempS = "I";
					}else if(tempS.startsWith("Exit")){
						tempS = "O";
					}else if(tempS.startsWith("Wall")){
						tempS = "x";
					}else if(tempS.startsWith("Remove")){
						tempS = "f";
					}
					((WarGameMapModel) o).setElements(Integer.parseInt(positionXinLoadCbox.getSelectedItem().toString()),Integer.parseInt(positionYinLoadCbox.getSelectedItem().toString()),tempS);
//					JOptionPane.showMessageDialog(null, "Edit successfully");				
//					positionsXinLoad.setText("");
//					positionsYinLoad.setText("");
//					elementinLoad.setText("");
					((WarGameMapModel) o).showMap();
					saveInLoad.setEnabled(true);
					fileCbox.setEnabled(false);
					showMapButton.setEnabled(false);
				}
			});
			
			saveInLoad.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						((WarGameMapModel) o).setMapName(fileCbox.getSelectedItem().toString());
						((WarGameMapModel) o).saving();
						fileCbox.setEnabled(true);
					} catch (IOException e1) {
//						e1.printStackTrace();		
					}
				}
			});
			
			loadFrame.add(loadPanel);
			loadFrame.setContentPane(loadPanel);
			loadFrame.setVisible(true);
			loadFrame.setLayout(null);
			break;
			
		//create map
		case 5:
			final JFrame createFrame = new JFrame("Create new map");
			createFrame.setBounds(300, 200, 800, 600);
			createFrame.setSize(700,500);
			this.setLocation(0,0);
			createFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
			final JPanel formPanel = new JPanel();
			final JPanel homePanel = new JPanel();
			GridBagLayout newGbl = new GridBagLayout();
			formPanel.setLayout(newGbl);
			GridBagConstraints newC = new GridBagConstraints();
			newC.fill = GridBagConstraints.HORIZONTAL;
			
			final JButton createButton = new JButton("Create");
			createButton.setEnabled(false);
			final JButton showButton = new JButton("Show map");
			final JButton setButton = new JButton("Set");
			final JButton saveButton = new JButton("Save");
			final JButton checkButton = new JButton("Check");

			
			final JLabel mapNameLabel = new JLabel("Map name: ");
			final JTextField mapNameText = new JTextField(10);
			newC.gridx = 1;
			newC.gridy = 0;
			formPanel.add(mapNameLabel,newC);
			newC.gridx = 2;
			newC.gridy = 0;
			formPanel.add(mapNameText,newC);	
			JLabel mapSizeLabel = new JLabel("Set size:(height*width)");
			newC.gridx = 1;
			newC.gridy = 1;
			formPanel.add(mapSizeLabel,newC);
			
			int mapSize[] = new int[25];
			
//			final JTextField mapHeightText = new JTextField(10);
			final JComboBox<Integer> mapHeightCbox = new JComboBox<Integer>();
			mapHeightCbox.setEditable(true);
			mapHeightCbox.setEnabled(false);
			for (int i = 10; i < mapSize.length+1; i++) {
				mapHeightCbox.addItem(i);
			}
			newC.gridx = 2;
			newC.gridy = 1;
//			formPanel.add(mapHeightText,newC);
			formPanel.add(mapHeightCbox,newC);
			
//			final JTextField mapWidthText = new JTextField(10);
			final JComboBox<Integer> mapWidthCbox = new JComboBox<Integer>();
			mapWidthCbox.setEditable(true);
			mapWidthCbox.setEnabled(false);
			for (int i = 10; i < mapSize.length+1; i++) {
				mapWidthCbox.addItem(i);
			}
			newC.gridx = 3;
			newC.gridy = 1;
//			formPanel.add(mapWidthText,newC);
			formPanel.add(mapWidthCbox,newC);

//			String tempElement[] = {"x", "i","h","m","I","O"};
			
			final JLabel mapElementLabel = new JLabel("Set Element:");
			newC.gridx = 1;
			newC.gridy = 3;
			formPanel.add(mapElementLabel,newC);
			JLabel label1 = new JLabel("(position x, position y, element)");
			newC.gridx = 1;
			newC.gridy = 4;
			formPanel.add(label1,newC);
			
//			String tempMapSize[][] = ((WarGameMapModel) o).getMap();
			
//			final JTextField positionsX = new JTextField(10);
			final JComboBox<Integer> positionXCbox = new JComboBox<Integer>();
			positionXCbox.setEditable(false);
			positionXCbox.setEnabled(false);
//			positionsX.setEditable(false);
//			for (int i = 1; i < tempMapSize.length+1; i++) {
//				positionXCbox.addItem(i);
//			}
			newC.gridx = 2;
			newC.gridy = 3;
//			formPanel.add(positionsX,newC);
			formPanel.add(positionXCbox,newC);
			
//			final JTextField positionsY = new JTextField(10);
			final JComboBox<Integer> positionYCbox = new JComboBox<Integer>();
			positionYCbox.setEditable(false);
			positionYCbox.setEnabled(false);
//			positionsY.setEditable(false);
//			for (int i = 1; i < tempMapSize[0].length+1; i++) {
//				positionYCbox.addItem(i);
//			}
			newC.gridx = 3;
			newC.gridy = 3;
//			formPanel.add(positionsY,newC);
			formPanel.add(positionYCbox,newC);
			
//			final JTextField element = new JTextField(10);
			final JComboBox<String> elementCbox = new JComboBox<String>();
//			elementCbox.setEditable(false);
			elementCbox.setEnabled(false);
//			element.setEditable(false);
//			for (String string : tempElement) {
//				elementCbox.addItem(string);
//			}
			newC.gridx = 4;
			newC.gridy = 3;
//			formPanel.add(element,newC);
			formPanel.add(elementCbox,newC);

			
			//Show the map
			showButton.setEnabled(false);
			newC.gridx = 4;
			newC.gridy = 6;
			formPanel.add(showButton,newC);
			showButton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					((WarGameMapModel) o).showMap();
				}
			});
			

//			Check the name if the map file already exsited
			newC.gridx = 3;
			newC.gridy = 0;
			formPanel.add(checkButton,newC);
			checkButton.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					if (mapNameText.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Empty map name!");
					}else{
					((WarGameMapModel)o).listing();
					ArrayList<Boolean> check = new ArrayList<Boolean>();
					ArrayList<String> mapFileToCheck = new ArrayList<String>();
					mapFileToCheck = ((WarGameMapModel)o).getMapFileName();
					for (String s : mapFileToCheck) {
						if (mapNameText.getText().equals(s)) {
							JOptionPane.showMessageDialog(null, "Map file already existed!");
							mapNameText.setText("");
							check.add(false);
						}else{
							check.add(true);
						}
					}
					if (!check.contains(false)) {	
						JOptionPane.showMessageDialog(null, "Select map size!");
						createButton.setEnabled(true);
						mapHeightCbox.setEnabled(true);
						mapWidthCbox.setEnabled(true);
						mapNameText.setEditable(false);
						checkButton.setEnabled(false);
					}
					}
				}
			});

//			Create the map by user-defined name and size
			newC.gridx = 2;
			newC.gridy = 2;
			formPanel.add(createButton,newC);
			createButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Integer.parseInt(mapHeightCbox.getSelectedItem().toString())<10||Integer.parseInt(mapWidthCbox.getSelectedItem().toString())<10) {
						JOptionPane.showMessageDialog(null, "Map too small!");
					}else if(Integer.parseInt(mapHeightCbox.getSelectedItem().toString())>25||Integer.parseInt(mapWidthCbox.getSelectedItem().toString())>25){
						JOptionPane.showMessageDialog(null, "Map too big!");
					}else{
//					((WarGameMapModel) o).setMapHeight(Integer.parseInt(mapHeightText.getText()));
//					((WarGameMapModel) o).setMapWidth(Integer.parseInt(mapWidthText.getText()));
					((WarGameMapModel) o).setMapHeight(Integer.parseInt(mapHeightCbox.getSelectedItem().toString()));
					((WarGameMapModel) o).setMapWidth(Integer.parseInt(mapWidthCbox.getSelectedItem().toString()));
					((WarGameMapModel) o).setMapName(mapNameText.getText());
					((WarGameMapModel) o).createMap(((WarGameMapModel) o).getMapName(), ((WarGameMapModel) o).getMapHeight(), ((WarGameMapModel) o).getMapWidth());

					String map[][] = ((WarGameMapModel) o).getMap();
					for (int i = 1; i < map.length+1; i++) {
						positionXCbox.addItem(i);
					}
					for (int j = 1; j < map[0].length+1; j++) {
						positionYCbox.addItem(j);
					}
					String mapName = ((WarGameMapModel) o).getMapName();
					JOptionPane.showMessageDialog(null, mapName+" created successfully. Map size: "+map.length+"*"+map[0].length+".");	
//					JOptionPane.showMessageDialog(null, "Please set elements");
					setButton.setEnabled(true);
					createButton.setEnabled(false);
					showButton.setEnabled(true);
					saveButton.setEnabled(true);
//					positionsX.setEditable(true);
//					positionsY.setEditable(true);
//					positionXCbox.setEditable(true);
					positionXCbox.setEnabled(true);
//					positionYCbox.setEditable(true);
					positionYCbox.setEnabled(true);
					
//					element.setEditable(true);
					elementCbox.setEnabled(true);
					
					mapNameText.setEditable(false);
//					mapHeightText.setEditable(false);
//					mapWidthText.setEditable(false);
					mapHeightCbox.setEditable(false);
					mapHeightCbox.setEnabled(false);
					mapWidthCbox.setEditable(false);
					mapWidthCbox.setEnabled(false);
					map = ((WarGameMapModel) o).getMap();

					ArrayList<String> characters = new ArrayList<String>();
					ArrayList<String> items = new ArrayList<String>();
					try {
						((WarGameMapModel) o).listCharacter();
						((WarGameMapModel) o).listItems();
					} catch (IOException e1) {
//						e1.printStackTrace();
					}
					characters = ((WarGameMapModel) o).getCharacterName();
					items = ((WarGameMapModel) o).getItemName();
					
					if (characters!=null) {					
						characters = ((WarGameMapModel) o).getCharacterName();
						
						for (String s : characters) {
							elementCbox.addItem("character"+s);
							elementCbox.addItem("monster"+s);
						}
					}
					
					if (items!=null) {
						items = ((WarGameMapModel) o).getItemName();
						
						for (String s : items) {
							elementCbox.addItem("item"+s);
						}
					}
					
					elementCbox.addItem("Entry door");
					elementCbox.addItem("Exit door");
					elementCbox.addItem("Wall");
					elementCbox.addItem("Remove");

				}
				}
			});
			
			//Set the elements in the map
			setButton.setEnabled(false);
			newC.gridx = 3;
			newC.gridy = 4;
			formPanel.add(setButton,newC);
			setButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					((WarGameMapModel) o).setElements(Integer.parseInt(positionsX.getText()),Integer.parseInt(positionsY.getText()),element.getText());
					String tempS;
					tempS = elementCbox.getSelectedItem().toString();
					if (tempS.startsWith("character")) {
						tempS = "h";
					}else if(tempS.startsWith("monster")){
						tempS = "m";
					}else if(tempS.startsWith("item")){
						tempS = "i";
					}else if(tempS.startsWith("Entry")){
						tempS = "I";
					}else if(tempS.startsWith("Exit")){
						tempS = "O";
					}else if(tempS.startsWith("Wall")){
						tempS = "x";
					}else if(tempS.startsWith("Remove")){
						tempS = "f";
					}
					((WarGameMapModel) o).setElements(Integer.parseInt(positionXCbox.getSelectedItem().toString()), Integer.parseInt(positionYCbox.getSelectedItem().toString()), tempS);
//					JOptionPane.showMessageDialog(null, "Set successfully");				
//					positionsX.setText("");
//					positionsY.setText("");
//					element.setText("");
					((WarGameMapModel) o).showMap();
				}
			});
			
			//Save the map
			saveButton.setEnabled(false);
			newC.gridx = 4;
			newC.gridy = 4;
			formPanel.add(saveButton,newC);
			saveButton.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						((WarGameMapModel) o).saving();
					} catch (IOException e1) {
//						e1.printStackTrace();		
					}
					if(((WarGameMapModel) o).validateMap()==true){
					createFrame.setVisible(false);
					}
				}
			});
			homePanel.add(formPanel);
			createFrame.add(homePanel);
			createFrame.setContentPane(homePanel);
			createFrame.setVisible(true);
			createFrame.setLayout(null);
			break;
			
//		map saving show errors
		case 6:
			JOptionPane.showMessageDialog(null, ((WarGameMapModel) o).getErrorMsg());	
			break;
			
		default:
			break;
		}
	}
	
}
package warGame.View;
import warGame.Model.*;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class WarGameMapView extends JFrame implements Observer{
	private JTextField textField;

	@Override
	public void update(Observable o, Object arg) {
	    JFrame frame = new JFrame();
		
	    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\map\\hero.jpg"));
	    frame.setResizable(false);
	    frame.setLocationByPlatform(true);
	    frame.setVisible(true);
	    frame.setTitle("Maps --- by:SiboW");
	    frame.setSize(1280, 800);
	    frame.getContentPane().setLayout(null);
		
		JPanel manipulatePanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.setBounds(0, 40, 520, 720);
		frame.getContentPane().add(manipulatePanel);
		manipulatePanel.setLayout(null);
		
		JLabel lblMapName = new JLabel("Map name:");
		lblMapName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblMapName.setBounds(30, 70, 120, 30);
		manipulatePanel.add(lblMapName);
		
		JLabel lblMapSize = new JLabel("Map size:");
		lblMapSize.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblMapSize.setBounds(30, 120, 120, 30);
		manipulatePanel.add(lblMapSize);
		
		textField = new JTextField();
		textField.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		textField.setBounds(150, 70, 120, 30);
		manipulatePanel.add(textField);
		textField.setColumns(10);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnCheck.setBounds(310, 70, 120, 30);
		manipulatePanel.add(btnCheck);
		
		JComboBox<Integer> heightCbox = new JComboBox<Integer>();
		heightCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		heightCbox.setBounds(150, 125, 55, 21);
		manipulatePanel.add(heightCbox);
		
		JComboBox<Integer> widthCbox = new JComboBox<Integer>();
		widthCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		widthCbox.setBounds(215, 125, 55, 21);
		manipulatePanel.add(widthCbox);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnCreate.setBounds(310, 120, 120, 30);
		manipulatePanel.add(btnCreate);
		
		JLabel lblCreateNewMap = new JLabel("Create New Map");
		lblCreateNewMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateNewMap.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblCreateNewMap.setBounds(180, 10, 200, 30);
		manipulatePanel.add(lblCreateNewMap);
		
		JLabel lblSetElements = new JLabel("Set elements:");
		lblSetElements.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblSetElements.setBounds(30, 170, 120, 30);
		manipulatePanel.add(lblSetElements);
		
		JComboBox<Integer> positionXCbox = new JComboBox<Integer>();
		positionXCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		positionXCbox.setBounds(150, 175, 55, 21);
		manipulatePanel.add(positionXCbox);
		
		JComboBox<Integer> positionYCbox = new JComboBox<Integer>();
		positionYCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		positionYCbox.setBounds(215, 175, 55, 21);
		manipulatePanel.add(positionYCbox);
		
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(560, 40, 720, 720);
		frame.getContentPane().add(displayPanel);
		displayPanel.setLayout(null);
		
		JLabel lblMapnametoshow = new JLabel("MapNameToShow");
		lblMapnametoshow.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblMapnametoshow.setHorizontalAlignment(SwingConstants.CENTER);
		lblMapnametoshow.setBounds(800, 0, 160, 30);
		frame.getContentPane().add(lblMapnametoshow);
		
	}
}

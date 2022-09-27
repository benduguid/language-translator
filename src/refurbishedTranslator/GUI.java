package refurbishedTranslator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame{
	
	private JTextField firstTextField;
	private JTextField secondTextField;
	private final JComboBox<String> firstLanguage;
	private final JComboBox<String> secondLanguage;
	private JPanel panel;
	private JFrame frame;
	private JButton button;
	static String language1;
	static String language2;
	
	static Translator myTranslator = new Translator();
	
	public GUI() {
		
		
		String[] choices = {"English", "Spanish", "German","Dutch","French"};
	    firstLanguage = new JComboBox<String>(choices);
	    firstLanguage.setBounds(10, 30, 80 ,25);
	    
	    firstTextField = new JTextField(50);
	    firstTextField.setBounds(200, 20, 250, 50);
	    	    
	    secondLanguage = new JComboBox<String>(choices);
	    secondLanguage.setBounds(10, 110, 80 ,25);
	    
	    secondTextField = new JTextField(50);
	    secondTextField.setBounds(200, 100, 250, 50);
	    
	    button = new JButton("Translate");
	    button.setBounds(10, 70, 100, 25);
	    button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	language1 = (String) firstLanguage.getSelectedItem();
            	language2 = (String) secondLanguage.getSelectedItem();
            	Map<String, Integer> firstLanguageHashMap = Translator.firstLanguageToHashMap1(language1);
        		Map<Integer, String> secondLanguageHashMap = Translator.secondLanguageToHashMap1(language2);
        		String[] phraseArray = myTranslator.stringToArray1(firstTextField.getText());
        		int[] keys = myTranslator.getKeys1(phraseArray, firstLanguageHashMap, secondLanguageHashMap);
        		String translation = myTranslator.translatePhrase1(secondLanguageHashMap, keys);
        		secondTextField.setText(translation);
            }

        });
	    
		panel = new JPanel();
		panel.setLayout(null);
		panel.add(firstLanguage);
		panel.add(firstTextField);
		panel.add(secondLanguage);
		panel.add(secondTextField);
		panel.add(button);
		
		frame = new JFrame();
		frame.setSize(500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}

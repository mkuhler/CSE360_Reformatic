import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class Driver {
		JFrame mainFrame;
		Driver parent;
		JTextArea output;
		JTextArea errorLog;
		File inputFile;
		
		// Main method that will create a Driver object and open the main window
		public static void main(String[] args) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new Driver();
				}
			});
		}
				
		public Driver () {
			// Allows passing a reference to Driver object to JDialog
			// inside inner class of button action listener
			this.parent = this;
			
			// Set up of the JFrame
			mainFrame = new JFrame("Reformatic");
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setSize(800, 640);
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setLayout(new BorderLayout());
			
			// Create the grid layout that will be the basis of the columns
			GridLayout colLayout = new GridLayout(0, 1);
			colLayout.setVgap(10);
			colLayout.setHgap(10);
			
			// Left Column
			JPanel leftCol = new JPanel(colLayout);
			JLabel title = new JLabel("REFORMATIC");
			
			JButton loadBtn = new JButton("Load File");
			loadBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new fileInputDialog(parent);
				}
			});
			
			JButton saveBtn = new JButton("Save File");
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new textInputDialog(parent);
				}
			});
			
			JButton viewFlagsBtn = new JButton("View Flags");
			JButton quitBtn = new JButton("Quit");
			
			// Right Column
			JPanel rightCol = new JPanel();
			rightCol.setLayout(colLayout);
			
			JLabel outputTitle = new JLabel("OUTPUT");
			output = new JTextArea();
			JLabel errorLogTitle = new JLabel("ERROR LOG");
			errorLog = new JTextArea();
			
			// Add all of the elements to each column in the appropriate row
			leftCol.add(title, 0);
			leftCol.add(loadBtn, 1);
			leftCol.add(saveBtn, 2);
			leftCol.add(viewFlagsBtn, 3);
			leftCol.add(quitBtn, 4);
			
			rightCol.add(outputTitle, 0);
			rightCol.add(output, 1);
			rightCol.add(errorLogTitle, 2);
			rightCol.add(errorLog, 3);
			
			// Set column size to be half of the screen size
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			leftCol.setSize(screenSize.width/2, screenSize.height/2);
			rightCol.setSize(screenSize.width/2, screenSize.height/2);
			
			mainFrame.add(BorderLayout.WEST, leftCol);
			mainFrame.add(BorderLayout.EAST, rightCol);
			mainFrame.setVisible(true);
			
			// Change the font of all frame elements
			Font font = new Font("Montserrat", Font.BOLD, 14);
			changeFont(mainFrame, font);
		}
		
		public void setOutput (String filepath) {
			output.setText(filepath);
		}
		
		public void inputTextFile(File inputFile) {
			// Call function to format file
			output.setText("FORMATTED LOL");
		}
		
		public void changeFont (Component comp, Font font) {
			comp.setFont(font);
			
			if (comp instanceof Container) {
				for (Component child : ((Container) comp).getComponents()) {
					changeFont(child, font);
				}
			}
		}
		
		public void changeBtnStyle (JButton btn) {
			//btn.setBackground();
		}
}

class textInputDialog extends JDialog implements ActionListener  {

	private static final long serialVersionUID = 1L;
	
	private Driver parent;
	private JButton textInBtn;
	private JTextField filepathField;
	private String filepath;
	
	public textInputDialog(Driver parent) {
		super();
		setModal(true);
		setSize(420, 420);
		setLayout(new GridLayout(0, 1));
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.parent = parent;
		
		//dialogPanel = new JPanel();
		JLabel dialogTitle = new JLabel("Format File");
		textInBtn = new JButton("Save As");
		filepathField = new JTextField();
		
		add(dialogTitle);
		add(filepathField);
		add(textInBtn);
		
		textInBtn.addActionListener(this);
		
		//add(dialogPanel);
		setSize(new Dimension(420, 420));
		setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj.equals(textInBtn)) {
			filepath = filepathField.getText();
			parent.setOutput(filepath);
			dispose();
		}
	}
	
	public String getFilepath() {
		return filepath;
	}
}

class fileInputDialog extends JDialog implements ActionListener  {
	private static final long serialVersionUID = 1L;
	
	private Driver parent;
	private JLabel enterFileTitle;
	private JButton enterFileBtn;
	//private JTextField filepathField;
	
	private JFileChooser fileChooser;
	private File file;
	
	public fileInputDialog(Driver parent) {
		super();
		setModal(true);
		setSize(420, 420);
		setLayout(new GridLayout(0, 1));
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.parent = parent;
		
		//dialogPanel = new JPanel();
		enterFileTitle = new JLabel("Enter File");
		enterFileBtn = new JButton("Reformat");
		//filepathField = new JTextField();
		
		add(enterFileTitle);
		//add(filepathField);
		add(enterFileBtn);
		
		enterFileBtn.addActionListener(this);
		
		//add(dialogPanel);
		setSize(new Dimension(420, 420));
		setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj.equals(enterFileBtn)) {
			fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				parent.inputTextFile(file);
				// CHECK IF CORRECT FILE TYPE
				dispose();
			}
		}
	}
	
	public File getFile() {
		return file;
	}
}



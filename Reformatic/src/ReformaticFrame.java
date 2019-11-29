import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.io.*;
import javax.swing.filechooser.FileSystemView;

public class ReformaticFrame extends JFrame 
{
	private JPanel centerPanel;
	private JButton loadBtn, saveBtn, viewFlagsBtn, quitBtn;
	private JTextField output, error;
	private ActionListener listener;
	private Processing processor; 
	private JFileChooser fc;
	ReformaticFrame()
	{
		fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		listener = new ChoiceListener(); 
		processor = new Processing();
		createCenterPanel();
		add(centerPanel);
		setSize(800,400);
	}
	
	//TODO: add the function for each button call 
	public class ChoiceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == loadBtn)
				loadFile();
			if(event.getSource() == saveBtn)
				saveFile();
			if(event.getSource() == viewFlagsBtn)
				System.out.println("View Flags button pressed");
			if(event.getSource() == quitBtn)
				System.exit(0);
		}
	}
	
	public void createCenterPanel()
	{
		// create the center panel 
		centerPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		
		// create col layout
		leftPanel = createLeftPanel();
		JPanel rightPanel = new JPanel();
		rightPanel = createRightPanel();
		
		// build cols with buttons and outputs
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(leftPanel);
		centerPanel.add(rightPanel);
	}
	public JPanel createLeftPanel() 
	{
		// setup the left panel 
        GridLayout colLayout = new GridLayout(0, 1);
        colLayout.setVgap(10);
        colLayout.setHgap(10);
		JPanel leftPanel = new JPanel(colLayout);
		leftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Reformatic"));
	   
        // add each function button to panel 
		// and add listener to each button for action
        loadBtn = new JButton("Load File");
        leftPanel.add(loadBtn,0);
        loadBtn.addActionListener(listener);

        saveBtn = new JButton("Save File");
        leftPanel.add(saveBtn,1);
        saveBtn.addActionListener(listener);

        viewFlagsBtn = new JButton("View Flags");
        leftPanel.add(viewFlagsBtn,2);
        viewFlagsBtn.addActionListener(listener);
        
        quitBtn = new JButton("Quit");
        leftPanel.add(quitBtn,3);
        quitBtn.addActionListener(listener);
 
		return leftPanel;
	}
	public JPanel createRightPanel()
	{
		// create the right panel 
        GridLayout colLayout = new GridLayout(0, 1);
        colLayout.setVgap(10);
        colLayout.setHgap(10);
		JPanel rightPanel = new JPanel(colLayout);
		
		// create the output panel 
		output = new JTextField();
		output.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(output); 
		outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Output"));
		outputScrollPane.setPreferredSize(new Dimension(250, 50));
        rightPanel.add(outputScrollPane, 0);

        // create the error panel 
		error = new JTextField();
		error.setEditable(false);
		JScrollPane errorScrollPane = new JScrollPane(error); 
		errorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        errorScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Error"));
        rightPanel.add(errorScrollPane, 1);
		
        return rightPanel;
	}
	
	public void loadFile() 
	{
		String filename = "";
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			filename = selectedFile.getAbsolutePath();
		}
		
		try {
			processor.readFile(filename);
		} 
		catch (IOException e) {
			//TODO: Add error output  
		}
	}
	
	public void saveFile() 
	{
		String filename = "";
		int returnValue = fc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			filename = selectedFile.getAbsolutePath();
		}
		
		try {
			processor.Save(filename);
		} 
		catch (IOException e) {
			//TODO: Add error output  
		}
	}
}
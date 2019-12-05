import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

public class ReformaticFrame extends JFrame 
{
	// panel components 
	private JPanel centerPanel;
	private JButton loadBtn, saveBtn, clearBtn, viewFlagsBtn, quitBtn;
	private JTextArea output, error;
	private ActionListener listener;
	private Processing processor; 
	private JFileChooser fc;
	
	// error logging components 
	private boolean fileLoaded = false;
	private String errorLog = "";
	
	// error strings
	private final String INVAL_FLG_ERR = "INVALID FLAG";
	private final String MULTI_FLG_ERR = "MULTIPLE FLAGS";
	private final String EPTY_DOC_ERR = "EMPTY DOCUMENT";
	private final String CORR_DOC_ERR = "CORRUPTED DOCUMENT";
	private final String UNSUP_FILE_ERR = "UNSUPPORTED FILETYPE";
	private final String FILE_NOT_FOUND = "FILE NOT FOUND";
	private final String INVAL_SAVE = "INVALID SAVE";
	
	ReformaticFrame()
	{
		fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		listener = new ChoiceListener(); 
		processor = new Processing();
		createCenterPanel();
		add(centerPanel);
		
		// set basic frame properties
		setSize(1280,640);
		setTitle("Reformatic - A file reformatting tool");
	}
	
	public class ChoiceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == loadBtn)
				loadFile();
			if(event.getSource() == saveBtn)
				saveFile();
			if(event.getSource() == clearBtn) 
				clearOutput();
			if(event.getSource() == viewFlagsBtn)
				displayFlags();
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
		JPanel topPanel = new JPanel(colLayout);
		JPanel bottomPanel = new JPanel(colLayout);
		centerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Reformatic"));
	   
        // add each function button to panel 
		// and add listener to each button for action
        loadBtn = new JButton("Load File");
        bottomPanel.add(loadBtn,0);
        loadBtn.addActionListener(listener);

        saveBtn = new JButton("Save File");
        bottomPanel.add(saveBtn,1);
        saveBtn.addActionListener(listener);
        
        clearBtn = new JButton("Clear");
        bottomPanel.add(clearBtn,2);
        clearBtn.addActionListener(listener);

        viewFlagsBtn = new JButton("View Flags");
        bottomPanel.add(viewFlagsBtn,3);
        viewFlagsBtn.addActionListener(listener);
        
        quitBtn = new JButton("Quit");
        bottomPanel.add(quitBtn,4);
        quitBtn.addActionListener(listener);
        
        //create an image holder to hold the logo
       BufferedImage image = null;
        try {                
        	image = ImageIO.read(new File("src/reformaticLogo.png"));
        } catch (IOException ex) {
              System.out.println("Frame Error Encountered");
        }
        //Image resizeImg = image.getImage(); // transform it 
        Image newimg = image.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        JLabel picLabel = new JLabel(new ImageIcon(newimg));
        
        topPanel.add(picLabel);
        
        // add the panels to the leftPanel 
        leftPanel.add(topPanel,0);
        leftPanel.add(bottomPanel,1);
        
        
		return leftPanel;
	}
	public JPanel createRightPanel()
	{
		// create the right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		// create the output panel 
		output = new JTextArea();
		output.setEditable(false);
		output.setLineWrap(true);
		
        JScrollPane outputScrollPane = new JScrollPane(output); 
		outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Output"));
		outputScrollPane.setPreferredSize(new Dimension(80, 300));
		
		rightPanel.add(outputScrollPane, BorderLayout.CENTER);

        // create the error panel 
		error = new JTextArea();
		error.setEditable(false);
		error.setLineWrap(true);
		
		Font outputFont = new Font("Courier", Font.BOLD,12);
		output.setFont(outputFont);
		error.setFont(outputFont);
		
		JScrollPane errorScrollPane = new JScrollPane(error); 
		errorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        errorScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Error"));
		errorScrollPane.setPreferredSize(new Dimension(80, 200));

        
        rightPanel.add(errorScrollPane, BorderLayout.SOUTH);
		
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
		
		if(filename.contains(".txt"))
		{
			try {
				processor.readFile(filename);
			} 
			catch (IOException e) {
				//this means the file was either not found, or out of permissions
				updateErrorLog(FILE_NOT_FOUND);
			}
			catch (Exception e) {
				updateErrorLog("Uknown Error: " + e);
			}
			
			fileLoaded = true;
			ArrayList<Paragraph> finalOutput = new ArrayList<Paragraph>();
			finalOutput = processor.getOutput();
			
			// Looping through each line and printing the output to the text area
			for(Paragraph p : finalOutput){
				for(int i =0; i<p.getFormattedSize(); i++) {
					String line = p.getFormattedLine(i);
					output.append(line + "\n");
					
					try {
						line.getBytes(StandardCharsets.US_ASCII);
					}
					catch(Exception e) {
						// if this occurs, the file is not all in ascii
						fileLoaded = false;
						updateErrorLog(CORR_DOC_ERR + "- file input discarded");
						clearOutput();
						break;
					}
				}
			}
			
		}
		else 
		{
			//this means the file was not supported, since .txt is not in extension
			updateErrorLog(UNSUP_FILE_ERR);
		}
	}
	
	public void saveFile() 
	{
		if(fileLoaded)
		{
			String filename = "";
			int returnValue = fc.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();
				filename = selectedFile.getAbsolutePath();
			}
		
			try {
				processor.Save(filename);
				processor.clear();
			} 
			catch (IOException e) {
				//TODO: Add error output  
			}
		}
		else
		{
			// if we get here, it means a file has not been loaded yet
			// which means we need to error out 
			updateErrorLog(INVAL_SAVE);
			
		}
	}
	
	public void clearOutput() {
		output.setText(null);
		processor.clear();
	}
	
	public void displayFlags() 
	{
		String tab = "     ";
		String flags = "List of Flags:\n" +
				"-r" + tab + "Right Justification\n" +
				"-c" + tab + "		Center Justification\n" +
				"-l" + tab + "		Left Justification\n" +
				"-t" + tab + "		Title\n" +
				"\n" +
				"-d" + tab + "	Double Space\n" +
				"-s" + tab + "		Single Space\n" +
				"\n" +
				"-i" + tab + "		Indent on First Line\n" +
				"-b" + tab + "		Block Text\n" +
				"\n" +
				"-2" + tab + "	Two Column Layout\n" +
				"-1" + tab + "	One Column Layout\n" +
				"-e" + tab + "		Blank Line";
		
		JOptionPane.showMessageDialog(null, flags);
	}
	
	public static void changeFont ( Component component, Font font )
	{
	    component.setFont ( font );
	    if ( component instanceof Container )
	    {
	        for ( Component child : ( ( Container ) component ).getComponents () )
	        {
	            changeFont ( child, font );
	        }
	    }
	}
	
	private void updateErrorLog(String errorStatement)
	{
		errorLog += "ERROR: "+ errorStatement + "\n";
		error.setText(errorLog);
	}
}

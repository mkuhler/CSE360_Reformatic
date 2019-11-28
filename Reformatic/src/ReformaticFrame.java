/*
 * Zion L. Basque
 * 14-10-2017
 * A program that simulates a word processor
 */
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;


import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class ReformaticFrame extends JFrame implements KeyListener
{
	private JPanel centerPanel;
	private JCheckBox wCount, vCount, cCount;
	private JTextField output, error;
	private ActionListener listener;
	ReformaticFrame()
	{
		listener = new ChoiceListener(); 
		createCenterPanel();
		add(centerPanel);
		setSize(800,400);
	}
	
	public class ChoiceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
		}
	}
	public void createCenterPanel()
	{
		centerPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		leftPanel = createLeftPanel();
		JPanel rightPanel = new JPanel();
		rightPanel = createRightPanel();
		
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(leftPanel);
		centerPanel.add(rightPanel);
	}
	public JPanel createLeftPanel() 
	{
        GridLayout colLayout = new GridLayout(0, 1);
        colLayout.setVgap(10);
        colLayout.setHgap(10);

		JPanel leftPanel = new JPanel(colLayout);
		leftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Reformatic"));
	   
        
        JButton loadBtn = new JButton("Load File");
        leftPanel.add(loadBtn,0);

        JButton saveBtn = new JButton("Save File");
        leftPanel.add(saveBtn,1);

        JButton viewFlagsBtn = new JButton("View Flags");
        leftPanel.add(viewFlagsBtn,2);
        
        JButton quitBtn = new JButton("Quit");
        leftPanel.add(quitBtn,3);

		return leftPanel;
	}
	public JPanel createRightPanel()
	{
        GridLayout colLayout = new GridLayout(0, 1);
        colLayout.setVgap(10);
        colLayout.setHgap(10);

		JPanel rightPanel = new JPanel(colLayout);
		
		output = new JTextField();
		output.setEditable(false);

        JScrollPane outputScrollPane = new JScrollPane(output); 
		outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Output"));
		outputScrollPane.setPreferredSize(new Dimension(250, 50));
        
        
        rightPanel.add(outputScrollPane, 0);

        
		error = new JTextField();
		error.setEditable(false);

		JScrollPane errorScrollPane = new JScrollPane(error); 
		errorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        errorScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Error"));
        
        rightPanel.add(errorScrollPane, 1);
		
        return rightPanel;
	}

    public void keyPressed(KeyEvent e)
    { 
    }
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }



}

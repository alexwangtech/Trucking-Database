/**
 * Components that the "client" has requested:
 * Company
 * New
 * List
 * Invoices
 * Pay Bills
 * Deposits & Withdraw
 * Adjustment
 * A/R Aging Report
 * A/P Aging Report
 */

import panels.CompanyPanel;
import panels.InvoicePanel;
import panels.ListPanel;

import windows.LoadMaker;
import windows.ConnectionSetter;
import windows.DatabaseSetup;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
	// ===========================================
	// COMPONENTS. (DECLARED, BUT NOT INITIALIZED)
	// ===========================================
	GridBagConstraints c;
	JSplitPane splitPane;
	JPanel botInit, topMain;
	JButton cmpnyBtn, newBtn, listBtn, invoicesBtn, payBillsBtn, depWithBtn, adjustBtn, ararBtn, aparBtn, cnnctBtn, exitBtn, connBtn, setupBtn;
	
	public MainWindow()
	{
		// =====================================================
		// COMPONENT SET-UP SECTION OF THE GUI (FOR MAIN WINDOW)
		// =====================================================
		
		// top section of the splitPane. each button will either redirect the bottom panel to a different section, or open up a new window
		// the panel will use a flow layout (the default layout)
		cmpnyBtn = new JButton("Company");				// company button
		newBtn = new JButton("New");					// new button
		listBtn = new JButton("List");					// list button
		invoicesBtn = new JButton("Invoices");			// invoices button
		payBillsBtn = new JButton("Pay Bills");			// pay bills button
		depWithBtn = new JButton("Deposit & Withdraw");	// deposit & withdraw button
		adjustBtn = new JButton("Adjustment");			// adjustment button
		ararBtn = new JButton("A/R Aging Report");		// a/r aging report button
		aparBtn = new JButton("A/P Aging Report");		// a/p aging report button
		connBtn = new JButton("SQL Connection");		// SQL connection settings button
		setupBtn = new JButton("Database Setup");		// database setup button
		
		// create the top panel and add the buttons to it
		topMain = new JPanel();
		topMain.add(cmpnyBtn);
		topMain.add(newBtn);
		topMain.add(listBtn);
		topMain.add(invoicesBtn);
		topMain.add(payBillsBtn);
		topMain.add(depWithBtn);
		topMain.add(adjustBtn);
		topMain.add(ararBtn);
		topMain.add(aparBtn);
		topMain.add(connBtn);
		topMain.add(setupBtn);
		
		// create the bottom panel (initial panel, shows up only during the start of the program)
		botInit = new JPanel();
		
		// create and set up the split pane
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);	// vertical split
		splitPane.setDividerLocation(40);						// divider location 40
		
		// add the top and bottom panels to the split pane
		splitPane.setTopComponent(topMain);
		splitPane.setBottomComponent(botInit);
		
		// add the splitPane to the frame, and set up the frame (this) [keep invisible until user goes through the log in prompt]
		this.add(splitPane);
		this.pack();
		this.setTitle("Dynasty Database");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
		
		// ==================================================
		// FUNCTIONALITY SECTION OF THE GUI (FOR MAIN WINDOW)
		// ==================================================
		
		// ActionListener for the 'Company' button: calls an instance of CompanyPanel and sets it as the bottom component of splitPane
		cmpnyBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				splitPane.setBottomComponent(new CompanyPanel());
			}
		});
		
		// ActionListener for the 'New' button: opens an instance of LoadMaker (new window)
		newBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new LoadMaker(); } });
			}
		});
		
		// ActionListener for the list button
		listBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				splitPane.setBottomComponent(new ListPanel());
			}
		});
		
		// ActionListener for the 'Invoices' button: calls an instance of InvoicePanel and sets it as the bottom component of splitPane.
		invoicesBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				splitPane.setBottomComponent(new InvoicePanel());
			}
		});
		
		// ActionListener for the 'Pay Bills' button
		payBillsBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				
			}
		});
		
		// ActionListener for the 'Deposit & Withdraw' button
		depWithBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				
			}
		});
		
		// ActionListener for the 'Adjustment' button
		adjustBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				
			}
		});
		
		// ActionListener for the 'A/R Aging Report' button
		ararBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				
			}
		});
		
		// ActionListener for the 'A/P Aging Report' button
		aparBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				
			}
		});
		
		// ActionListener for the 'SQL Connection' button: opens up an instance of ConnectionSetter (new window)
		connBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new ConnectionSetter(); } });
			}
		});
		
		// ActionListener for the 'Database Setup' button: sets up the COMPANIES table in the SQL server (calls instance of DatabaseSetup)
		setupBtn.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new DatabaseSetup(); } });
			}
		});
		
		// ====================================================
		// END OF THE FUNCTIONALITY SECTION FOR THE MAIN WINDOW
		// ====================================================
		
		// ==================================================================
		// LOG IN SETUP: USER MUST LOG IN BEFORE THE MAIN APPLICATION APPEARS
		// ==================================================================
		JFrame setupFrame = new JFrame("Log In");				// setup frame
		JPanel setupPanel = new JPanel();						// setup panel
		JLabel setupUsernameLabel = new JLabel("Username: ");	// username label
		JTextField setupUsernameField = new JTextField(20);		// username text field
		JLabel setupPasswordLabel = new JLabel("Password: ");	// password label
		JTextField setupPasswordField = new JTextField(20);		// password text field
		JButton setupEnterButton = new JButton("Enter");		// enter button
		JButton setupExitButton = new JButton("Exit");			// exit button
		
		// organize components in 'setupPanel' using GridBagLayout
		setupPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.ipadx = 1; c.ipady = 1;
		c.gridx = 0; c.gridy = 0; setupPanel.add(setupUsernameLabel, c);
		c.gridx = 1; c.gridy = 0; setupPanel.add(setupUsernameField, c);
		c.gridx = 0; c.gridy = 1; setupPanel.add(setupPasswordLabel, c);
		c.gridx = 1; c.gridy = 1; setupPanel.add(setupPasswordField, c);
		c.gridx = 0; c.gridy = 2; setupPanel.add(setupEnterButton, c);
		c.gridx = 1; c.gridy = 2; setupPanel.add(setupExitButton, c);
		
		// add 'setupPanel' to 'setupFrame', and set the frame visible
		setupPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setupFrame.add(setupPanel);
		setupFrame.pack();
		setupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupFrame.setLocationRelativeTo(null);
		setupFrame.setVisible(true);
		
		// ActionListener for 'setupEnterButton'. when pressed, application will check whether the login info is valid.
		setupEnterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					// there is a text file containing the needed info in the 'vitals' folder
					File txtfile = new File("./vitals/login_info.txt");
					FileReader reader = new FileReader(txtfile);
					BufferedReader buffer = new BufferedReader(reader);
					
					// get all of the needed info from text fields and the text file
					String enteredUsername = setupUsernameField.getText();
					String enteredPassword = setupPasswordField.getText();
					String expectedUsername = buffer.readLine();
					String expectedPassword = buffer.readLine();
					
					// close resources
					buffer.close();
					reader.close();	
					
					// if the strings all match, close this frame and open up the main application window (this)
					// else, display a dialog stating that the entered info is invalid
					if (enteredUsername.equals(expectedUsername) && enteredPassword.equals(expectedPassword))
					{
						setupFrame.dispose();
						setVisible(true);
					}
					else
						JOptionPane.showMessageDialog(null, "Invalid login or password. Please try again.");
				}
				catch (FileNotFoundException e)
				{
					JOptionPane.showMessageDialog(null, "Vital Login Info Not Found");
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help.");
				}
			}
		});
		
		// ActionListener for 'setupExitButton'. when pressed, application will end.
		setupExitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
	}
	
	// main method (driver method): runs the GUI
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new MainWindow(); } });
	}
}

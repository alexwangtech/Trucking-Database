package windows;

// ========================================================================================================================
// this class is used for setting the SQL connection that MainWindow/other windows will use for storing and retrieving data
// ========================================================================================================================

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class ConnectionSetter extends JFrame
{
	// ===========================================
	// COMPONENTS. (DECLARED, BUT NOT INITIALIZED)
	// ===========================================
	GridBagConstraints c;
	JPanel upperpanel, lowerpanel, mainpanel;
	JLabel connectionLabel, usernameLabel, passwordLabel;
	JTextField connectionField, usernameField, passwordField;
	JButton testButton, saveButton, exitButton;
	
	public ConnectionSetter()
	{
		// ===================================
		// COMPONENT SET-UP SECTION OF THE GUI
		// ===================================
		
		// FIRST INITIALIZATION BLOCK
		connectionLabel = new JLabel("Connection String: ");	// connection label
		connectionField = new JTextField(30);					// connection text field
		usernameLabel = new JLabel("Username: ");				// username label
		usernameField = new JTextField(30);						// username text field
		passwordLabel = new JLabel("Password: ");				// password label
		passwordField = new JTextField(30);						// password text field
		
		// SECOND INITIALIZATION BLOCK
		testButton = new JButton("Test Connection");	// test connection button
		saveButton = new JButton("Save & Exit");		// save & exit button
		exitButton = new JButton("Exit Only");			// exit button
		
		// add the components in the first initialization block to 'upperpanel' using GridBagLayout
		upperpanel = new JPanel(); upperpanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.ipadx = 1; c.ipady = 1;
		c.gridx = 0; c.gridy = 0; upperpanel.add(connectionLabel, c);
		c.gridx = 1; c.gridy = 0; upperpanel.add(connectionField, c);
		c.gridx = 0; c.gridy = 1; upperpanel.add(usernameLabel, c);
		c.gridx = 1; c.gridy = 1; upperpanel.add(usernameField, c);
		c.gridx = 0; c.gridy = 2; upperpanel.add(passwordLabel, c);
		c.gridx = 1; c.gridy = 2; upperpanel.add(passwordField, c);
		
		// add the components in the second initialization block to 'lowerpanel' using GridBagLayout
		lowerpanel = new JPanel(); lowerpanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; lowerpanel.add(testButton, c);
		c.gridx = 1; c.gridy = 0; lowerpanel.add(saveButton, c);
		c.gridx = 2; c.gridy = 0; lowerpanel.add(exitButton, c);
		
		// add 'upperpanel' and 'lowerpanel' to 'mainpanel' using GridBagLayout
		mainpanel = new JPanel(); mainpanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; mainpanel.add(upperpanel, c);
		c.gridx = 0; c.gridy = 1; mainpanel.add(lowerpanel, c);
		
		// add 'mainpanel' to the frame (this), and set up the frame
		mainpanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(mainpanel);
		this.setTitle("Server Parameters");
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		// ================================
		// FUNCTIONALITY SECTION OF THE GUI
		// ================================
		
		// set the text fields so that initially, they display the data that is currently saved as the SQL information
		try
		{
			// there is a text file that contains the SQL information, located in the 'vitals' folder
			File txtFile = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(txtFile);
			BufferedReader reader = new BufferedReader(in);
			
			// get the data from the text file
			String fileConnLink = reader.readLine();	// connection link (line 1)
			String fileUsername = reader.readLine();	// username        (line 2)
			String filePassword = reader.readLine();	// password        (line 3)
			
			// close the resources
			reader.close();
			in.close();
			
			// set the text fields to the retrieved strings
			connectionField.setText(fileConnLink);
			usernameField.setText(fileUsername);
			passwordField.setText(filePassword);
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Vital SQL File Not Found");
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help.");
		}
		
		// ActionListener for the 'Test' button: attempt a connection to an SQL server using the given data.
		testButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// get the information from the text fields
				String tempLink = connectionField.getText();
				String tempName = usernameField.getText();
				String tempPass = passwordField.getText();
				
				// attempt a connection to the SQL server, and display a message dialog notifying whether connection was successful or not
				try
				{
					Connection tempConn = DriverManager.getConnection(tempLink, tempName, tempPass);
					tempConn.close();
					JOptionPane.showMessageDialog(null, "Connection Successful");
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(null, "Connection Failed");
				}
			}
		});
		
		// ActionListener for the 'Save' button: save the information in the text fields and dispose the window
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					// find the text file that contains the SQL information, located in the 'vitals' folder
					File txtFile = new File("./vitals/sql_info.txt");
					FileWriter out = new FileWriter(txtFile);
					BufferedWriter writer = new BufferedWriter(out);
					
					// write the data in the text fields to the file
					writer.write(connectionField.getText()); writer.newLine();
					writer.write(usernameField.getText());   writer.newLine();
					writer.write(passwordField.getText());   writer.newLine();
					
					// close the resources
					writer.close();
					out.close();
					
					// display a dialog notifying that the data has been saved
					JOptionPane.showMessageDialog(null, "Saved!");
					
					// dispose the window
					dispose();
				}
				catch (FileNotFoundException e)
				{
					JOptionPane.showMessageDialog(null, "Vital SQL File Not Found");
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help.");
				}
			}
		});
		
		// ActionListener for the 'Exit' button: dispose the window without making any changes to the data in the text file
		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				dispose();
			}
		});
	}
	
	// TEMPORARY RUN METHOD
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ConnectionSetter();
			}
		});
	}
}

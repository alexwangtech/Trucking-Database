// list panel of the application.

package panels;

import windows.LoadMaker;
import windows.LoadEditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class ListPanel extends JPanel
{
	GridBagConstraints c;
	JPanel panel1, panel2;
	JScrollPane scrollPane;
	JLabel loadLabel, customerLabel, carrierLabel, originLabel, destinationLabel, truckLabel, driverLabel;
	JTextField loadTextField, customerTextField, carrierTextField, originTextField, destinationTextField, truckTextField, driverTextField;
	JButton searchButton, clearButton, newButton, openButton, deleteButton;
	JTable loadListTable;
	String[] tableNames;
	String[][] tableValues;
	String sqlUrl, sqlUsername, sqlPassword;
	ListTableModel tableModel;
	
	public ListPanel()
	{
		// set the values for 'sqlUrl', 'sqlUsername', and 'sqlPassword' using the method getSqlConnectionInfo().
		getSqlConnectionInfo();
		
		// ====================== //
		// initialized components //
		// ====================== //
		newButton = new JButton("New");							// 'New' button
		openButton = new JButton("Open");						// 'Open' button
		deleteButton = new JButton("Delete");					// 'Delete' button
		clearButton = new JButton("Clear Filter / Refresh");	// 'Clear' button
		searchButton = new JButton("Search");					// 'Search' button
		
		loadLabel = new JLabel("Load#");				// 'Load#' label
		customerLabel = new JLabel("Customer");			// 'Customer' label
		carrierLabel = new JLabel("Carrier");			// 'Carrier' label
		originLabel = new JLabel("Origin");				// 'Origin' label
		destinationLabel = new JLabel("Destination");	// 'Destination' label
		truckLabel = new JLabel("Truck#");				// 'Truck#' label
		driverLabel = new JLabel("Driver");				// 'Driver' label
		
		loadTextField = new JTextField(10);			// load# text field
		customerTextField = new JTextField(10);		// customer text field
		carrierTextField = new JTextField(10);		// carrier text field
		originTextField = new JTextField(10);		// origin text field
		destinationTextField = new JTextField(10);	// destination text field
		truckTextField = new JTextField(10);		// truck# text field
		driverTextField = new JTextField(10);		// driver text field
		
		// create the top most panel and add its corresponding components
		panel1 = new JPanel(); panel1.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; panel1.add(newButton, c);
		c.gridx = 1; c.gridy = 0; panel1.add(openButton, c);
		c.gridx = 2; c.gridy = 0; panel1.add(deleteButton, c);
		
		// create the second panel from the top and add its corresponding components
		panel2 = new JPanel(); panel2.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 1; c.gridy = 0; panel2.add(loadLabel, c);
		c.gridx = 2; c.gridy = 0; panel2.add(customerLabel, c);
		c.gridx = 3; c.gridy = 0; panel2.add(carrierLabel, c);
		c.gridx = 4; c.gridy = 0; panel2.add(originLabel, c);
		c.gridx = 5; c.gridy = 0; panel2.add(destinationLabel, c);
		c.gridx = 6; c.gridy = 0; panel2.add(truckLabel, c);
		c.gridx = 7; c.gridy = 0; panel2.add(driverLabel, c);
		
		c.gridx = 0; c.gridy = 1; panel2.add(clearButton, c);
		c.gridx = 1; c.gridy = 1; panel2.add(loadTextField, c);
		c.gridx = 2; c.gridy = 1; panel2.add(customerTextField, c);
		c.gridx = 3; c.gridy = 1; panel2.add(carrierTextField, c);
		c.gridx = 4; c.gridy = 1; panel2.add(originTextField, c);
		c.gridx = 5; c.gridy = 1; panel2.add(destinationTextField, c);
		c.gridx = 6; c.gridy = 1; panel2.add(truckTextField, c);
		c.gridx = 7; c.gridy = 1; panel2.add(driverTextField, c);
		c.gridx = 8; c.gridy = 1; panel2.add(searchButton, c);
		
		// create the JTable using the custom table model, and add it to the JScrollPane.
		setTableValues();
		tableNames = new String[] {"Load#", "Entered", "Customer", "Phone", "Ref#", "Carrier", "Phone", "Origin", "Destination", "Truck#", "Driver", "Cell"};
		tableModel = new ListTableModel(tableNames, tableValues);
		loadListTable = new JTable(tableModel);
		loadListTable.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(loadListTable);
		
		// add all components to the main panel (this)
		setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; add(panel1, c);
		c.gridx = 0; c.gridy = 1; add(panel2, c);
		c.weightx = 1; c.weighty = 1; c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; c.gridy = 2; add(scrollPane, c);
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		// ================================ //
		// FUNCTIONALITY SECTION OF THE GUI //
		// ================================ //
		
		// ActionListener for the 'New' button: opens up an instance of LoadMaker.
		newButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new LoadMaker(); } });
			}
		});
		
		// ActionListener for the 'Open' button: opens the selected load for editing.
		openButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int selectedID = Integer.valueOf(loadListTable.getValueAt(loadListTable.getSelectedRow(), 0).toString());
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new LoadEditor(selectedID); } });
			}
		});
				
		// ActionListener for the 'Delete' button: deletes the selected load from LOADS and LOADLIST tables in SQL server.
		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// get the ID of the selected load.
				int selectedID = Integer.valueOf(loadListTable.getValueAt(loadListTable.getSelectedRow(), 0).toString());
				
				// confirm that the user wants to delete the selected load item.
				int userInput = JOptionPane.showConfirmDialog(null, "Delete the selected item?", "Confirmation", JOptionPane.YES_NO_OPTION);
				
				// if the user did not select yes, return. else, create the statements needed to delete all associated objects.
				if (userInput == 1 || userInput == -1) return;
				else
				{
					ArrayList<String> arraylist = new ArrayList<String>();
					
					// command to delete the load from the LOADLIST table
					arraylist.add("DELETE FROM LOADLIST WHERE LOADID = " + selectedID + ";");
					
					// command to delete the load from the LOADS table.
					arraylist.add("DELETE FROM LOADS WHERE LOADID = " + selectedID + ";");
					
					// command to delete the associated LOADTABLELEFT table.
					arraylist.add("DROP TABLE LOADTABLELEFT" + selectedID + ";");
					
					// command to delete the associated LOADTABLERIGHT table.
					arraylist.add("DROP TABLE LOADTABLERIGHT" + selectedID + ";");
					
					// command to delete the associated NLBOTPANELLEFT table.
					arraylist.add("DROP TABLE NLBOTPANELLEFT" + selectedID + ";");
					
					// command to delete the associated NLBOTPANELRIGHT table.
					arraylist.add("DROP TABLE NLBOTPANELRIGHT" + selectedID + ";");
					
					// copy the data from the ArrayList to a String[] object
					String[] commandList = new String[arraylist.size()];
					for (int i = 0; i < arraylist.size(); i++) commandList[i] = arraylist.get(i);
					
					// execute the stored SQL statements.
					executeSQLCommands(commandList);
				}
			}
		});
		
		// ActionListener for the 'Clear Filter' button: clears the search text fields, and refreshes the table.
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				loadTextField.setText(null);
				customerTextField.setText(null);
				carrierTextField.setText(null);
				originTextField.setText(null);
				destinationTextField.setText(null);
				truckTextField.setText(null);
				driverTextField.setText(null);
				
				setTableValues();
				updateTable();
			}
		});
		
		// TODO: finish this action listener
		
		// ActionListener for the 'Search' button: searches for the loads that correspond to the entered parameters and displays them.
		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// a string array of the column names of the LOADLIST table
				String[] columnNames = new String[] {"LOADID", "CUSTOMER", "CARRIER_DRIVER", "ORIGIN", "DESTINATION", "TRUCK#", "DRIVER"};
				
				// get the values from the text fields and put them into a String[] array.
				String[] inputArray = new String[7];
				inputArray[0] = loadTextField.getText();		// load #
				inputArray[1] = customerTextField.getText();	// customer
				inputArray[2] = carrierTextField.getText();		// carrier
				inputArray[3] = originTextField.getText();		// origin
				inputArray[4] = destinationTextField.getText();	// destination
				inputArray[5] = truckTextField.getText();		// truck #
				inputArray[6] = driverTextField.getText();		// driver
				
				// make sure there is at least one non-null value
				boolean isEmpty = true;
				for (int i = 0; i < inputArray.length; i++)
					if (!(inputArray[i].equals("")))
							isEmpty = false;
				if (isEmpty) return;
							
				// create the SQL query command to be executed.
				StringBuilder stringbuilder = new StringBuilder();
				stringbuilder.append("SELECT * FROM LOADLIST WHERE ");
				for (int i = 0; i < inputArray.length; i++)
					if (!(inputArray[i].equals("")))
					{
						stringbuilder.append(columnNames[i] + " LIKE '%" + inputArray[i] + "%'");
						stringbuilder.append(" AND ");
					}
				
				// remove the last "and" word & finish the string.
				stringbuilder.delete(stringbuilder.length() - 4, stringbuilder.length());
				stringbuilder.append(";");
				
				try
				{
					// get a result set of the data using the executeSQLQuery() method.
					ResultSet resultset = executeSQLQuery(stringbuilder.toString());
					
					// copy the data from the ResultSet to an ArrayList
					ArrayList<String> templist = new ArrayList<String>();
					while (resultset.next())
						for (int i = 1; i <= 12; i++)
							templist.add(resultset.getString(i));
					
					// copy the values over to the String[][] array 'tableValues'
					tableValues = new String[templist.size() / 12][12];
					int counter = 0;
					for (int i = 0; i < tableValues.length; i++)
						for (int j = 0; j < tableValues[i].length; j++)
							tableValues[i][j] = templist.get(counter++);
					
					// call the update table method.
					updateTable();
				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		});
	}
	
	// method: gets the information for SQL connection (only needs to be called one time).
	private void getSqlConnectionInfo()
	{
		try
		{
			// the SQL connection info is located in the 'vitals' file
			File file = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);
			
			// read the file and set the information to the global scope variables.
			sqlUrl = reader.readLine();
			sqlUsername = reader.readLine();
			sqlPassword = reader.readLine();
			
			reader.close();
			in.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	// gets the information for the JTable from the SQL server and sets it to 'tableValues'.
	private void setTableValues()
	{
		try
		{
			// get a connection the SQL server and get the information.
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT * FROM LOADLIST;");
			
			// copy the ResultSet to a temporary ArrayList.
			ArrayList<String> tempList = new ArrayList<String>();
			while (resultset.next())
				for (int i = 1; i <= 12; i++)
					tempList.add(resultset.getString(i));
			
			// copy the data from the ArrayList to 'tableValues'.
			tableValues = new String[tempList.size() / 12][12];
			int counter = 0;
			for (int i = 0; i < tableValues.length; i++)
				for (int j = 0; j < tableValues[i].length; j++)
					tableValues[i][j] = tempList.get(counter++);
			
			resultset.close();
			statement.close();
			connection.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// method: updates the table.
	private void updateTable()
	{
		tableModel.setNewColumnValues(tableValues);
		tableModel.fireTableDataChanged();
	}
	
	// method: executes an SQL command (does not return a result set)
	private void executeSQLCommand(String command)
	{
		try
		{
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			
			statement.executeUpdate(command);
			
			statement.close();
			connection.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// method: executes a series of SQL commands.
	private void executeSQLCommands(String[] stringArray)
	{
		try
		{
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			
			for (String command : stringArray) statement.executeUpdate(command);
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// method: executes an SQL query and returns a result set.
	private ResultSet executeSQLQuery(String query)
	{
		try
		{
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			
			return resultset;
		}
		catch (SQLException e) { e.printStackTrace(); return null; }
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ListPanel panel = new ListPanel();
				JFrame frame = new JFrame("Temporary");
				frame.add(panel);
				
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

// custom table model class
class ListTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	String[] column_names;
	String[][] column_values;
	
	public ListTableModel(String[] columnNames, String[][] columnValues)
	{
		this.column_names = columnNames;
		this.column_values = columnValues;
	}
	
	public int getRowCount() { return column_values.length; }
	public int getColumnCount() { return column_names.length; }
	public String getColumnName(int columnIndex) { return column_names[columnIndex]; }
	public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
	public Object getValueAt(int row, int column) { return column_values[row][column]; }
	
	// custom added method: sets colum_values to a new String[][] input
	public void setNewColumnValues(String[][] columnValues) { this.column_values = columnValues; }
}

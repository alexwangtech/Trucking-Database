/**
 * company panel, part of the panels package
 * to be used by the main frame as an instance when the 
 * company button is clicked. sets the new panel as this panel.
 */

package panels;

import windows.CompanyMaker;
import windows.CompanyEditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class CompanyPanel extends JPanel
{
	GridBagConstraints c;
	JButton newCmpny, editCmpny, delCmpny, srchCmpny, refreshButton;
	JTextField srchTxtFld;
	JTable companiesTable;
	JScrollPane tablePane;
	JPanel topPanel;
	String[] tableColumns;
	String[][] tableValues;
	String sqlLink, sqlUsername, sqlPassword;
	
	public CompanyPanel()
	{
		// create the five main buttons
		newCmpny = new JButton("New Company");			// new company button
		editCmpny = new JButton("Edit Company");		// edit company button
		delCmpny = new JButton("Delete Company");		// delete company button
		srchCmpny = new JButton("Search Company");		// search company button
		refreshButton = new JButton("Refresh Table");	// refresh button
		
		// create a text field to be used with srchCmpny button
		srchTxtFld = new JTextField(30);
		
		// table columns for the companies table
		tableColumns = new String[] {"Database ID", "Company Name", "DBA", "MC#", "Tax ID", "Telephone", "Fax", "Rating", "Contract/Common", "Carrier?", "Broker?", "Customer?",
									 "Shipper?", "Consignee?", "Insurance Agency?", "Factoring?", "Preferred?", "Vendor?", "Owner/Operator?", "Other?"};
		
		// get the login info for the SQL server, located in the 'vitals' folder
		try
		{
			File sqlLoginFile = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(sqlLoginFile);
			BufferedReader reader = new BufferedReader(in);
			
			sqlLink = reader.readLine();
			sqlUsername = reader.readLine();
			sqlPassword = reader.readLine();
			
			reader.close();
			in.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		
		// get the data for the selected table columns from the SQL server and store it to 'tableValues'
		try
		{
			ResultSet resultSet = getTableData();
			
			// copy the data from the result_set to an ArrayList
			ArrayList<String> tempList = new ArrayList<String>();
			while (resultSet.next())
				for (int i = 0; i < tableColumns.length; i++)
					tempList.add(resultSet.getString(i + 1));
			
			// copy the data from the ArrayList to 'tableValues' (String[][] array)
			tableValues = new String[tempList.size() / tableColumns.length][tableColumns.length];
			int counter = 0;
			for (int i = 0; i < tableValues.length; i++)
				for (int j = 0; j < tableValues[i].length; j++)
					tableValues[i][j] = tempList.get(counter++);
		}
		catch (SQLException e) { e.printStackTrace(); }

		// create new JTable, using the CompaniesTableModel class
		CompaniesTableModel model = new CompaniesTableModel(tableColumns, tableValues);
		companiesTable = new JTable(model);
		companiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		companiesTable.setFillsViewportHeight(true);	// table uses entire height of container
		tablePane = new JScrollPane(companiesTable);	// add table to a scroll pane
		
		// add the rest of the components to a top panel
		topPanel = new JPanel();
		topPanel.add(newCmpny);
		topPanel.add(editCmpny);
		topPanel.add(delCmpny);
		topPanel.add(srchTxtFld);
		topPanel.add(srchCmpny);
		topPanel.add(refreshButton);
		
		// add topPanel and tablePanel to inherited JPanel (this) using GridBagLayout
		this.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0; c.gridy = 0; this.add(topPanel, c);
		c.weighty = 1; c.weightx = 1; c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; c.gridy = 1; this.add(tablePane, c);
		
		// ActionListener for "New Company" button: loads in an instance of CompanyMaker
		newCmpny.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new CompanyMaker(); } });
			}
		});
		
		// ActionListener for "Edit Company" button: loads in an instance of CompanyEditor with the selected table row
		// this will throw an ArrayIndexOutOfBoundsException if no row is selected.
		editCmpny.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					int selectedID = Integer.valueOf(companiesTable.getValueAt(companiesTable.getSelectedRow(), 0).toString());
					javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new CompanyEditor(selectedID); } });
				}
				catch (ArrayIndexOutOfBoundsException e) { return; }
			}
		});
		
		// ActionListener for 'Delete' button: deletes all information of the company at the selected row
		// displays an option dialog to the user, to confirm whether the user really wants to delete the company
		// this will throw an ArrayIndexOutOfBoundsException if no row is selected.
		delCmpny.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					// get the selected row's ID number
					int selectedID = Integer.valueOf(companiesTable.getValueAt(companiesTable.getSelectedRow(), 0).toString());
					
					// get user input from a confirmation dialog.
					// if the user selects "yes", delete the selected company, and all tables associated with it
					int userInput = JOptionPane.showConfirmDialog(null, "Delete the selected item?", "Confirmation", JOptionPane.YES_NO_OPTION);
					
					if (userInput == 1 || userInput == -1)
						return;
					else
					{						
						// make a connection to the SQL server & create a Statement object
						Connection connection = DriverManager.getConnection(sqlLink, sqlUsername, sqlPassword);
						Statement statement = connection.createStatement();
						
						// delete the company from the COMPANIES table
						statement.executeUpdate("DELETE FROM COMPANIES WHERE COMPANYID = " + selectedID + ";");
						
						// delete the associated COMPANYCONTACTS table
						statement.executeUpdate("DROP TABLE COMPANYCONTACTS" + selectedID + ";");
						
						// delete the associated COMPANYPAYABLEPANEL table
						statement.executeUpdate("DROP TABLE COMPANYPAYABLEPANEL" + selectedID + ";");
						
						// delete the associated PAYABLECONTACTS table
						statement.executeUpdate("DROP TABLE PAYABLECONTACTS" + selectedID + ";");
						
						// delete the associated COMPANYRECEIVABLEPANEL table
						statement.executeUpdate("DROP TABLE COMPANYRECEIVABLEPANEL" + selectedID + ";");
						
						// delete the associated RECEIVABLECONTACTS table
						statement.executeUpdate("DROP TABLE RECEIVABLECONTACTS" + selectedID + ";");
						
						// delete the associated INSURERCOMPANIES table
						statement.executeUpdate("DROP TABLE INSURERCOMPANIES" + selectedID + ";");
						
						// delete the associated INSURANCECONTACTS table
						statement.executeUpdate("DROP TABLE INSURANCECONTACTS" + selectedID + ";");
						
						statement.close();
						connection.close();
					}
				}
				catch (SQLException e) { e.printStackTrace(); }
				catch (ArrayIndexOutOfBoundsException e) { return; }
			}
		});
		
		// ActionListener for the 'Search Company' button: searches for all companies that contain the string in the text field
		srchCmpny.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				String sqlName, textFieldText = srchTxtFld.getText().toLowerCase();
				int substringIndex;
				
				try
				{
					// use the getTableData() method to get a result set of all companies + relevant info from the SQL server
					ResultSet resultset = getTableData();
					
					// sort through the result set, looking for only companies whose names contain the desired string
					ArrayList<String> tempList = new ArrayList<String>();
					while (resultset.next())
					{
						sqlName = resultset.getString(2).toLowerCase();
						substringIndex = sqlName.indexOf(textFieldText);
						if (substringIndex != -1)
							for (int i = 0; i < tableColumns.length; i++)
								tempList.add(resultset.getString(i + 1));
					}
					
					// copy the data from 'tempList' to 'tableValues'
					tableValues = new String[tempList.size() / tableColumns.length][tableColumns.length];
					int counter = 0;
					for (int i = 0; i < tableValues.length; i++)
						for (int j = 0; j < tableValues[i].length; j++)
							tableValues[i][j] = tempList.get(counter++);	
				}
				catch (SQLException e) { e.printStackTrace(); }
				
				// set column_values to the new data and update the table
				model.setNewColumnValues(tableValues);
				model.fireTableDataChanged();
			}
		});
		
		// ActionListener for the 'Refresh Table' button: re-retrieves the data from the SQL server and updates the table. also clears the search text field.
		refreshButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					// call the getTableData() method to retrieve a result set from the SQL server
					ResultSet resultSet = getTableData();
					
					// copy the data from the result_set to an ArrayList
					ArrayList<String> tempList = new ArrayList<String>();
					while (resultSet.next())
						for (int i = 0; i < tableColumns.length; i++)
							tempList.add(resultSet.getString(i + 1));
					
					// copy the data from the ArrayList to 'tableValues' (String[][] array)
					tableValues = new String[tempList.size() / tableColumns.length][tableColumns.length];
					int counter = 0;
					for (int i = 0; i < tableValues.length; i++)
						for (int j = 0; j < tableValues[i].length; j++)
							tableValues[i][j] = tempList.get(counter++);
				}
				catch (SQLException e) { e.printStackTrace(); }
				
				// set column_values to the new data and update the table.
				model.setNewColumnValues(tableValues);
				model.fireTableDataChanged();
				
				// clear the search text field.
				srchTxtFld.setText(null);
			}
		});
		
		// temporary, give this a border
		this.setBorder(BorderFactory.createLineBorder(Color.red));
		tablePane.setBorder(BorderFactory.createLineBorder(Color.red));
	}
	
	// method: makes a connection to the SQL server, retrieves all data necessary to the JTable, and returns it as a ResultSet object
	private ResultSet getTableData()
	{
		try
		{
			Connection connection = DriverManager.getConnection(sqlLink, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			ResultSet result_set = statement.executeQuery("SELECT COMPANYID, NAME, DBA, MC#, TAXID, TEL, FAX, RATING, CONTRACT_COMMON, ISCARRIER, ISBROKER, ISCUSTOMER, "
													    + "ISSHIPPER, ISCONSIGNEE, ISINSAGENCY, ISFACTORING, ISPREFERRED, ISVENDOR, ISOWNEROPERATOR, ISOTHER "
								   					    + "FROM COMPANIES;");
			return result_set;
		}
		catch (SQLException e) { e.printStackTrace(); return null; }
	}
	
	private static void runGUI()
	{
		JFrame frame = new JFrame();
		CompanyPanel panel = new CompanyPanel();
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// temporary run method
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { runGUI(); } });
	}
}

// TableModel for the 'companiesTable' JTable
class CompaniesTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	String[] column_names;
	String[][] column_values;
	
	public CompaniesTableModel(String[] columnNames, String[][] columnValues)
	{
		this.column_names = columnNames;
		this.column_values = columnValues;
	}
	
	// method: sets columns_values to a String[][] input
	public void setNewColumnValues(String[][] columnValuesInput)
	{
		this.column_values = columnValuesInput;
	}
	
	public int getRowCount()
	{
		return column_values.length;
	}
	
	public int getColumnCount()
	{
		return column_names.length;
	}

	public String getColumnName(int columnIndex)
	{
		return column_names[columnIndex];
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}
	
	public Object getValueAt(int row, int column)
	{
		switch (column)
		{
		case 0: return column_values[row][column]; 
		case 1: return column_values[row][column]; 
		case 2: return column_values[row][column]; 
		case 3: return column_values[row][column]; 
		case 4: return column_values[row][column];
		case 5: return column_values[row][column];
		case 6: return column_values[row][column];
		case 7: return column_values[row][column];
		case 8: return column_values[row][column];
		case 9: return column_values[row][column].equals("true")? "Yes" : "No";
		case 10: return column_values[row][column].equals("true")? "Yes" : "No";
		case 11: return column_values[row][column].equals("true")? "Yes" : "No";
		case 12: return column_values[row][column].equals("true")? "Yes" : "No";
		case 13: return column_values[row][column].equals("true")? "Yes" : "No";
		case 14: return column_values[row][column].equals("true")? "Yes" : "No";
		case 15: return column_values[row][column].equals("true")? "Yes" : "No";
		case 16: return column_values[row][column].equals("true")? "Yes" : "No";
		case 17: return column_values[row][column].equals("true")? "Yes" : "No";
		case 18: return column_values[row][column].equals("true")? "Yes" : "No";
		case 19: return column_values[row][column].equals("true")? "Yes" : "No";
		
		default: return null;
		}
	}
}

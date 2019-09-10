// a program that sets up the main tables for the SQL database.

package windows;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup
{
	private JLabel label;
	private JPanel panel;
	private JFrame frame;
	private String createCompaniesTableCommand = "CREATE TABLE COMPANIES"
							  				   + "("
							  				   + "COMPANYID       varchar(50), "
							  				   + "NAME            varchar(500), "
							  				   + "DBA             varchar(200), "
							  				   + "TEL             varchar(200), "
							  				   + "TAXID           varchar(200), "
							  				   + "FAX             varchar(200), "
							  				   + "MC#             varchar(200), "
							  				   + "ESTABLISHED     varchar(200), "
							  				   + "DOT#            varchar(200), "
							  				   + "SCAC            varchar(200), "
							  				   + "ADDRESS1        varchar(500), "
							  				   + "ADDRESS2        varchar(500), "
							  				   + "CITY            varchar(200), "
							  				   + "STATE           varchar(100), "
							  				   + "ZIP             varchar(100), "
							  				   + "RATING          varchar(100), "
							  				   + "CONTRACT_COMMON varchar(100), "
							  				   + "INSURANCEAGENCY varchar(500), "
							  				   + "ISCARRIER       varchar(50), "
							  				   + "ISBROKER        varchar(50), "
							  				   + "ISCUSTOMER      varchar(50), "
							  				   + "ISSHIPPER       varchar(50), "
							  				   + "ISCONSIGNEE     varchar(50), "
							  				   + "ISINSAGENCY     varchar(50), "
							  				   + "ISFACTORING     varchar(50), "
							  				   + "ISPREFERRED     varchar(50), "
							  				   + "ISVENDOR        varchar(50), "
							  				   + "ISOWNEROPERATOR varchar(50), "
							  				   + "ISOTHER         varchar(50)"
							  				   + ");";
	
	private String createLoadsTableCommand = "CREATE TABLE LOADS"
										   + "("
										   + "LOADID          varchar(50), "
										   + "DATEENTERED     varchar(50), "
										   + "EQUIP           varchar(100), "
										   + "LENGTH          varchar(100), "
										   + "AIRRIDE         varchar(50), "
										   + "LOGISTIC        varchar(50), "
										   + "TEAM            varchar(50), "
										   + "INSTRUCTION     varchar(1000), "
										   + "COMMODITY       varchar(1000), "
										   + "QUANTITY        varchar(100), "
										   + "UNIT            varchar(100), "
										   + "WEIGHT          varchar(100), "
										   + "BILLTO          varchar(500), "
										   + "ADDRESS         varchar(1000), "
										   + "BTCONTACT       varchar(500), "
										   + "BTEMAIL         varchar(500), "
										   + "BTTEL           varchar(100), "
										   + "BTFAX           varchar(100), "
										   + "BTCELL          varchar(100), "
										   + "BTREF#          varchar(100), "
										   + "BTDISCOUNT      varchar(100), "
										   + "BTTERM          varchar(100), "
										   + "BTNOTE          varchar(1000), "
										   + "PAYTO           varchar(1000), "
										   + "MC#             varchar(100), "
										   + "PTCONTACT       varchar(100), "
										   + "PTEMAIL         varchar(500), "
										   + "PTTEL           varchar(100), "
										   + "PTFAX           varchar(100), "
										   + "PTCELL          varchar(100), "
										   + "PTDRIVER        varchar(100), "
										   + "PTDCELL         varchar(100), "
										   + "WITHHOLDPAYMENT varchar(50), "
										   + "PTDTRUCK#       varchar(100), "
										   + "PTDTRAILER#     varchar(100), "
										   + "PTDDISCOUNT     varchar(100), "
										   + "PTDTERM         varchar(100), "
										   + "PTNOTE          varchar(1000)"
										   + ");";
	
	private String createListTableCommand = "CREATE TABLE LOADLIST"
										  + "("
										  + "LOADID         varchar(50), "
										  + "ENTERED        varchar(100), "
										  + "CUSTOMER       varchar(500), "
										  + "CUSTOMERPHONE  varchar(500), "
										  + "REF#           varchar(500), "
										  + "CARRIER_DRIVER varchar(500), "
										  + "CARRIERPHONE   varchar(500), "
										  + "ORIGIN         varchar(500), "
										  + "DESTINATION    varchar(500), "
										  + "TRUCK#         varchar(500), "
										  + "DRIVER         varchar(500), "
										  + "DRIVERCELL     varchar(500)"
										  + ");";
		
	public DatabaseSetup()
	{
		// setup of a frame that displays "setting up"
		label = new JLabel("Setting up...");
		panel = new JPanel();
		panel.add(label);
		frame = new JFrame("Database Setup");
		frame.add(panel);
		
		frame.setSize(500, 500);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		try
		{
			// retrieve the information from the sql_info.txt file in 'vitals' folder
			File file = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);
			
			String sqlLink = reader.readLine();
			String sqlUsername = reader.readLine();
			String sqlPassword = reader.readLine();
			
			reader.close();
			in.close();
			
			// make a connection to the SQL server and run the commandS
			Connection connection = DriverManager.getConnection(sqlLink, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			
			statement.executeUpdate(createCompaniesTableCommand);
			statement.executeUpdate(createLoadsTableCommand);
			statement.executeUpdate(createListTableCommand);
			
			statement.close();
			connection.close();
			
			// display a message notifying user that the setup is complete
			JOptionPane.showMessageDialog(null, "Setup complete!");			
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Database has already been set up.");
		}
		
		frame.dispose();
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new DatabaseSetup(); } });
	}
}

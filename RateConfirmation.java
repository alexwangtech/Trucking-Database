/**
 * To be used for the Dynasty Database GUI Application
 * Template for Rate Confirmation
 * 
 * refer to "rate confirmation.jpg" for information on the layout of the template
 */

package templates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.Borders;

public class RateConfirmation {
	final String companyName = "Dynasty Transportation Inc.";
	final String addressStreet = "4625 Centennial Lane";
	final String addressCityStateZip = "Ellicott City, MD 21042";
	final String homePhone = "(301)208-0882";
	final String faxNumber = "(301)208-0883";
	
	String loadNumber;
	String date;
	String carrier;
	String dispatch;
	String phone;
	String fax;
	String equipment;
	String commodity;
	String quantity;
	String units;
	String weight;
	
	String origin_pickupDate;
	String origin_time;
	String origin_company;
	String origin_address;
	String origin_city;
	String origin_st;
	String origin_zip;
	String origin_ref;
	String origin_notes;
	
	String destination_deliverDate;
	String destination_time;
	String destination_company;
	String destination_address;
	String destination_city;
	String destination_st;
	String destination_zip;
	String destination_contact;
	String destination_phone;
	String destination_ref;
	String destination_notes;
	
	String amount;
	
	String driver;
	String truckNumber;
	String cellNumber;
	String trailerNumber;
	
	//constructor: creates an instance of this class
	public RateConfirmation() {
		
	}
	
	//method: sets the load number
	public void setLoadNumber(String input) {
		loadNumber = input;
	}
	
	//method: sets the date
	public void setDate(String input) {
		date = input;
	}
	
	//method: sets the carrier
	public void setCarrier(String input) {
		carrier = input;
	}
	
	//method: sets the dispatch
	public void setDispatch(String input) {
		dispatch = input;
	}
	
	//method: sets the phone
	public void setPhone(String input) {
		phone = input;
	}
	
	//method: sets the fax
	public void setFax(String input) {
		fax = input;
	}
	
	//method: sets the equipment
	public void setEquipment(String input) {
		equipment = input;
	}
	
	//method: sets the commodity
	public void setCommodity(String input) {
		commodity = input;
	}
	
	//method: sets the quantity
	public void setQuantity(String input) {
		quantity = input;
	}
	
	//method: sets the units
	public void setUnits(String input) {
		units = input;
	}
	
	//method: sets the weight
	public void setWeight(String input) {
		weight = input;
	}
	
	//method: sets the pick up date (origin)
	public void setOriginPickupDate(String input) {
		origin_pickupDate = input;
	}
	
	//method: sets the time (origin)
	public void setOriginTime(String input) {
		origin_time = input;
	}
	
	//method: sets the company (origin)
	public void setOriginCompany(String input) {
		origin_company = input;
	}
	
	//method: sets the address (origin)
	public void setOriginAddress(String input) {
		origin_address = input;
	}
	
	//method: sets the city (origin)
	public void setOriginCity(String input) {
		origin_city = input;
	}
	
	//method: sets the state (origin)
	public void setOriginState(String input) {
		origin_st = input;
	}
	
	//method: sets the zipcode (origin)
	public void setOriginZip(String input) {
		origin_zip = input;
	}
	
	//method: sets the reference number (origin)
	public void setOriginRef(String input) {
		origin_ref = input;
	}
	
	//method: sets the notes (origin)
	public void setOriginNotes(String input) {
		origin_notes = input;
	}
	
	//method: sets the deliver date (destination)
	public void setDestinationDeliverDate(String input) {
		destination_deliverDate = input;
	}
	
	//method: sets the time (destination)
	public void setDestinationTime(String input) {
		destination_time = input;
	}
	
	//method: sets the company (destination)
	public void setDestinationCompany(String input) {
		destination_company = input;
	}
	
	//method: sets the address (destination)
	public void setDestinationAddress(String input) {
		destination_address = input;
	}
	
	//method: sets the city (destination)
	public void setDestinationCity(String input) {
		destination_city = input;
	}
	
	//method: sets the state (destination)
	public void setDestinationState(String input) {
		destination_st = input;
	}
	
	//method: sets the zipcode (destination)
	public void setDestinationZip(String input) {
		destination_zip = input;
	}
	
	//method: sets the contact (destination)
	public void setDestinationContact(String input) {
		destination_contact = input;
	}
	
	//method: sets the phone (destination)
	public void setDestinationPhone(String input) {
		destination_phone = input;
	}
	
	//method: sets the reference number (destination)
	public void setDestinationRef(String input) {
		destination_ref = input;
	}
	
	//method: sets the notes (destination)
	public void setDestinationNotes(String input) {
		destination_notes = input;
	}
	
	//method: creates the rate confirmation
	public void createTemplate() {
		try {
			//create a blank document
			@SuppressWarnings("resource")
			XWPFDocument document = new XWPFDocument();
			
			//create the file output stream
			FileOutputStream out = new FileOutputStream(new File(loadNumber + ".docx"));
			
			//company name, address, telephone, and fax (separate lines, center alignment)
			String[] topBlock = {companyName, addressStreet, addressCityStateZip, "Tel: " + homePhone, "Fax: " + faxNumber};
			XWPFParagraph paragraph;
			XWPFRun run;
			for (int i = 0; i < topBlock.length; i++) {
				paragraph = document.createParagraph();
				paragraph.setSpacingAfter(0);
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				run = paragraph.createRun();
				run.setFontSize(8);
				run.setFontFamily("Cambria");
				run.setText(topBlock[i]);
			}
			
			//add a blank line
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			
			//rate confirmation label (center alignment, bold, bordered)
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			paragraph.setSpacingAfter(0);
			paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
			paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
			paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
			paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
			run = paragraph.createRun();
			run.setFontSize(14);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("Rate Confirmation");
			
			//add a blank line
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			
			/* load number and date
			 * place load# at 1st position, and date at position 60
			 * make up for difference in the lengths of inputs
			 */
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			
			//create the LOAD# label (bold, cambria, 11)
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setBold(true);
			run.setFontFamily("Cambria");
			run.setText("LOAD#: ");
			
			//input load number (cambria, 11)
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(" " + loadNumber);
			
			//add spaces (date must be at position 60)
			for (int i = 0; i < 130 - loadNumber.length(); i++) {
				run.setText(" ");
			}
			
			//set date label and date
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("DATE: ");
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(" " + date);
			
			//blank line
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			
			//carrier information (left alignment is fine, carrier label is bold)
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setBold(true);
			run.setFontFamily("Cambria");
			run.setText("CARRIER: ");
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(carrier);
			
			/* create categories dispatch, phone, and fax
			 * place dispatch left, phone middle, and fax right
			 */
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setBold(true);
			run.setFontFamily("Cambria");
			run.setText("DISPATCH: ");
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setBold(false);
			run.setFontFamily("Cambria");
			run.setText(dispatch);
			for (int i = 0; i < 55 - dispatch.length(); i++) {
				run.setText(" ");
			}
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("PHONE: ");
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(" " + phone);
			for (int i = 0; i < 50 - phone.length(); i++) {
				run.setText(" ");
			}
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("FAX: ");
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(fax);
			
			//display the equipment
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("EQUIPMENT: ");
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(equipment);
			
			//display the commodity
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("COMMODITY");
			
			run = paragraph.createRun();
			run.setFontFamily("Cambria");
			run.setFontSize(9);
			run.setBold(false);
			run.setText(commodity);
			
			/* quantity, units, and weight
			 * must be evenly distributed on one line
			 */
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("QUANTITY: ");
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(quantity);
			for (int i = 0; i < 55 - quantity.length(); i++) {
				run.setText(" ");
			}
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("UNITS: ");
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(units);
			for (int i = 0; i < 50 - units.length(); i++) {
				run.setText(" ");
			}
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(true);
			run.setText("WEIGHT: ");
			
			run = paragraph.createRun();
			run.setFontSize(9);
			run.setFontFamily("Cambria");
			run.setBold(false);
			run.setText(weight);
			
			//create label named origin (centered, bold, cambria, 11)
			paragraph = document.createParagraph();
			paragraph.setSpacingAfter(0);
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run = paragraph.createRun();
			run.setFontSize(11);
			run.setBold(true);
			run.setFontFamily("Cambria");
			run.setText("***ORIGIN***");
			
			
			
			
			
/************************** DO NOT TOUCH ***********************************************/			
			document.write(out);
			out.close();
			
			System.out.println("done");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
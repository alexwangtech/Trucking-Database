/**
 * to be used as a temporary data holder during the creation
 * of a new company. when user selects "new contact", the previous
 * information will be stored in an object of this type, which will
 * then be stored in an arraylist of such objects
 */

package contacts;

public class CompanyContact {
	String name, cell, phone, fax, email;
	
	// constructor: creates an instance of this class
	public CompanyContact() {
		
	}
	
	// method: stores a name
	public void storeName(String name) {
		this.name = name;
	}
	
	// method: stores a cell number
	public void storeCell(String cellNumber) {
		this.cell = cellNumber;
	}
	
	// method: stores a phone number
	public void storePhone(String phoneNumber) {
		this.phone = phoneNumber;
	}
	
	// method: stores a fax number
	public void storeFax(String faxNumber) {
		this.fax = faxNumber;
	}
	
	// method: stores an email address
	public void storeEmail(String emailAddress) {
		this.email = emailAddress;
	}
	
	// method: returns stored name
	public String getName() {
		return this.name;
	}
	
	// method: returns a stored cell number
	public String getCell() {
		return this.cell;
	}
	
	// method: returns a stored phone number
	public String getPhone() {
		return this.phone;
	}
	
	// method: returns a stored fax number
	public String getFax() {
		return this.fax;
	}
	
	// method: returns a stored email address
	public String getEmail() {
		return this.email;
	}
}
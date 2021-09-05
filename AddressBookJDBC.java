package com.sqldemo;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
public class AddressBookJDBC {
	public enum IOService {
        DB_IO
    }
    private List<Contact> addressBookList;
	private Object addressBookFile;
    private static AddressBook addressBookDBService;
    public AddressBookJDBC() {
        addressBookDBService = AddressBook.getInstance();
    }
    public AddressBookJDBC(List<Contact> addresBookList) {
        this();
        this.addressBookList = addressBookList;
    }
    public List<Contact> readAddressBookData(IOService ioservice) {
        if (ioservice.equals(IOService.DB_IO))
			try {
				this.addressBookList = addressBookDBService.readData();
			} catch (AddressBookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return this.addressBookList;
    }
    
    public void updateRecord(String firstname, String address) throws AddressBookException {
        int result = addressBookDBService.updateAddressBookData(firstname, address);
        if (result == 0)
            return;
        Contact addressBookData = this.getAddressBookData(firstname);
        if (addressBookData != null)
            addressBookData.setAddress(address);
    }
    public boolean checkUpdatedRecordSyncWithDatabase(String firstname) throws AddressBookException {
        try {
            List<Contact> addressBookData = addressBookDBService.getAddressBookDataUsingDB(firstname);
            return addressBookData.get(0).equals(getAddressBookData(firstname));
        } catch (AddressBookException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
    }
    private Contact getAddressBookData(String firstname) {
        for (Contact addressBookItem : addressBookList) {
            if (addressBookItem.getFirstname().equals(firstname)) {
                return addressBookItem;
            }
        }
        return null;
    }
    
    public List<Contact> readAddressBookData(IOService ioService, String start, String end)
            throws AddressBookException {
        try {
            LocalDate startLocalDate = LocalDate.parse(start);
            LocalDate endLocalDate = LocalDate.parse(end);
            if (ioService.equals(IOService.DB_IO))
                return addressBookDBService.readData();
            return this.addressBookList;
        } catch (AddressBookException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
    }
    
    private Contact getAddressBookData1(String firstname) {
        return ((Collection<Contact>) this.addressBookFile).stream().filter(addressBookItem -> addressBookItem.getFirstname().equals(firstname))
                .findFirst().orElse(null);
    }
    
    public List<Contact> readAddressBookData1(IOService ioService, String start, String end)
            throws AddressBookException {
        try {
            LocalDate startLocalDate = LocalDate.parse(start);
            LocalDate endLocalDate = LocalDate.parse(end);
            if (ioService.equals(IOService.DB_IO))
                return addressBookDBService.readData(startLocalDate, endLocalDate);
            return this.addressBookList;
        } catch (AddressBookException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
    }
    public int readAddressBookData(String function, String city) throws AddressBookException {
        return addressBookDBService.readDataBasedOnCity(function, city);
    }
    public void addNewContact(String firstName, String lastName, String address, String city, String state, String zip,
            String phone, String email,String AddressBookType, String date) throws AddressBookException {
addressBookList.add(addressBookDBService.addNewContact(firstName, lastName, address, city, state, zip, phone,
email,AddressBookType, date));
}
} 
}

package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.*;
import controllers.BookController;
import controllers.BookCopyController;
import exceptions.BookCopyException;
import exceptions.LoginException;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.AdministratorHomePage;
import librarysystem.BothHomePage;
import librarysystem.LibrarianHomePage;
import librarysystem.guiElements.UtilGui;

public class SystemController implements ControllerInterface {

	public static Auth currentAuth = null;
	private DataAccess da = new DataAccessFacade();

	public void login(String id, String password) throws LoginException {

		
		HashMap<String, User> map = da.readUserMap();
        loginAuthentication(map , id , password);

	}
	@Override
	public List<String> allMemberIds() {
		
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		
		List<String> retval = new ArrayList<>();
		List<Book> retBook = new ArrayList<>();

		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	/**
	 *  Add New book
	 * @param member
	 */
	public void saveLibraryMember(LibraryMember member){
		da.saveNewMember(member);
	}

	@Override
	public boolean addBook(String isbn , String title , int maxBorrowDays, List<Author> authors) throws BookCopyException {

		Book book = new Book(isbn, title, maxBorrowDays, authors);
		BookController bookController = new BookController();
		bookController.addNewBook(book);
		return true;
	}

	public void saveBook(Book book){
		BookController bookController = new BookController();
		bookController.addNewBook(book);
	}

	/**
	 *
	 * @param ISBN
	 * @param copyNumber
	 * @return
	 */
	public boolean addBookCopy(String ISBN , int copyNumber) throws BookCopyException{

		BookCopyController bookCopyController = new BookCopyController();
		 if(bookCopyController.copyNumberTakenCheck(copyNumber, ISBN))
			 throw  new BookCopyException("There is no book with ISBN = " + ISBN);
		 bookCopyController.addNewBookCopy(ISBN);
		return true;
	}

	
	public HashMap<String, LibraryMember> getMembers() {
		
		return da.readMemberMap();
	}

	public boolean checkMemberId(String member_id){
		if(!allMemberIds().contains(member_id.trim()))
			return false;
		return true;
	}

	@Override
	public Book checkBookISBN(String isbn) {

		if(!allBookIds().contains(isbn))
			return null;
		return getBooks().get(isbn);
	}

	@Override
	public HashMap<String, Book> getBooks() {
		 
		 return da.readBooksMap();
	}


	public Address addAddress(String street, String city , String state , String zip){
		return new Address(street, city, state, zip);
	}

	@Override
	public LibraryMember addLibraryMember(String memberNumber, String firstName, String lastName, String phoneNumber, Address address) {
		return new LibraryMember(memberNumber, firstName, lastName, phoneNumber, address);
	}

	@Override
	public void deleteMember(LibraryMember memberId) {
		da.deleteMember(memberId);
	}


	private void loginAuthentication(HashMap<String, User> map, String id, String password) throws LoginException {

		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Username/Password combination is incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		UtilGui.hideAllWindows();


		if(currentAuth.name().equals("LIBRARIAN")){
			if(!LibrarianHomePage.INSTANCE.isInitialized()){
				LibrarianHomePage.INSTANCE.init();
			}

			LibrarianHomePage.INSTANCE.setVisible(true);
		}
		else if(currentAuth.name().equals("ADMIN")){
			if(!AdministratorHomePage.INSTANCE.isInitialized())
				AdministratorHomePage.INSTANCE.init();
			AdministratorHomePage.INSTANCE.setVisible(true);
		}
		else if(currentAuth.name().equals("BOTH")){

			if(!AdministratorHomePage.INSTANCE.isInitialized())
				BothHomePage.INSTANCE.init();
			BothHomePage.INSTANCE.setVisible(true);

		}else{
			throw new LoginException("Cannot Authorize");
		}

	}


}

package librarysystem.validation;

import librarysystem.LoginScreen;
import librarysystem.guiElements.book.AddBookCopyPanel;
import librarysystem.guiElements.book.BookGui;
import librarysystem.guiElements.member.EditOrDeleteMember;
import librarysystem.guiElements.member.MemberUI;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;
import librarysystem.guiElements.checkOut.PrintMemberCheckOut;
import librarysystem.guiElements.member.SearchMemberPanel;

import java.awt.*;
import java.util.HashMap;


final public class ValidationFactory {
	private ValidationFactory(){}
	static HashMap<Class<? extends Component>, Validation> map = new HashMap<>();
	static {
		map.put(BookGui.class, new BookValidation());
		map.put(MemberUI.class, new MemberValidation());
		map.put(LoginScreen.class, new LoginValidation());
		map.put(CheckOutBookPanel.class, new CheckOutValidation());
		map.put(SearchMemberPanel.class, new SearchMemberValidation());
		map.put(SearchBookPanel.class, new SearchBookValidation());
		map.put(OverDuePanel.class, new OverDueValidation());
		map.put(PrintMemberCheckOut.class, new PrintMemberCheckOutValidation());
		map.put(AddBookCopyPanel.class, new BookCopyValidation());
		map.put(EditOrDeleteMember.class,  new EditOrDeleteMemberValidation());
	}
	public static Validation getValidation(Component c) {
		Class<? extends Component> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No Validation found for this Component");
		}
		return map.get(cl);
	}
}

package librarysystem.validation;

import exceptions.*;

import librarysystem.Util;
import librarysystem.guiElements.member.MemberUI;

import javax.swing.*;
import java.awt.*;

public class MemberValidation implements Validation {

	private MemberUI memberGui;

	@Override
	public void checkValidation(Component ob) throws ValidationException {

		memberGui = (MemberUI) ob;
		checkEmptyFields();
		checkPhoneNumeric();
		checkZipNumeric();
		checkState();
	}

	private void checkEmptyFields() throws ValidationException {

		for (JTextField field : memberGui.getMemberFields()) {
			if (field.getText().isEmpty())
				throw new ValidationException("All fields must be non-empty");
		}

	}

	private void checkPhoneNumeric() throws ValidationException {
		String val = memberGui.getMemberFields()[3].getText();
		String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
			if (!val.matches(pattern)) {
				throw new ValidationException("Phone Number should be numeric or 10 digits");
			}
	}

	private void checkZipNumeric() throws ValidationException {
		String val = memberGui.getMemberFields()[memberGui.getMemberFields().length - 1].getText().trim();
		try {
			Integer.parseInt(val);
		} catch (NumberFormatException e) {
			throw new ValidationException("Zipcode must be numeric");
		}
		if (val.length() != 5)
			throw new ValidationException("Zipcode must have 5 digits");
	}

	private void checkState() throws ValidationException {

		String state = memberGui.getMemberFields()[memberGui.getMemberFields().length - 2].getText().trim();
		if (state.length() != 2)
			throw new ValidationException("State field must have two characters");
		if (!Util.isInRangeAtoZ(state.charAt(0)) || !Util.isInRangeAtoZ(state.charAt(1))) {
			throw new ValidationException("Characters is state field must be in range A-Z");
		}
	}

}

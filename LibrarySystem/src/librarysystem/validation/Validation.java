package librarysystem.validation;

import exceptions.ValidationException;

import java.awt.*;

public interface Validation {
	public void checkValidation(Component ob) throws ValidationException;
}

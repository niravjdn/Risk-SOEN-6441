package com.risk6441.exception;

/**
 * This class defines user defined exception.
 * @author Nirav
 */
public class InvalidMapException extends Exception{

	/**
	 * This method throws user defined exception if map is not valid.
	 * @param message message for the exception
	 */
	public InvalidMapException(String message) {
		super(message);
		
	}
}

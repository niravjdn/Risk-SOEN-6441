package com.risk6441.exception;

/**
 * This class is used to handle any type of Invalid Game Move exceptions.
 * 
 * @author Rohan 
 * @version 1.0.0
 *
 */
public class InvalidGameActionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * This method throws a user defined exception if a map is not valid.
	 * @param message message for the exception
	 */
	public InvalidGameActionException(String message) {
		super(message);
	}
}

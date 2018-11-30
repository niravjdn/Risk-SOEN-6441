package com.risk6441.config;

/**
 * This class defines static properties for the game such as number of armies according to the number of players.
 * @author Nirav
 */
public class Config {

	/**
	 * The ARMIES_TWO_PLAYER Constant
	 */
	public static final Integer ARMIES_TWO_PLAYER = 35;//35

	/**
	 *The message to be printed in text after after attack is completed 
	 */
	public static String message = "";
	
	/**
	 * The ARMIES_THREE_PLAYER Constant
	 */
	public static final Integer ARMIES_THREE_PLAYER = 5;//35
	
	/**
	 * The ARMIES_FOUR_PLAYER Constant
	 */
	public static final Integer ARMIES_FOUR_PLAYER = 30;
	
	/**
	 * The ARMIES_FIVE_PLAYER Constant
	 */
	public static final Integer ARMIES_FIVE_PLAYER = 25;
	
	/**
	 * The ARMIES_SIX_PLAYER Constant
	 */
	public static final Integer ARMIES_SIX_PLAYER = 20;
	
	/**
	 * The isGameOver
	 */
	public static boolean isGameOver = false;

	public static boolean isAllComputerPlayer = true;
	
	public static int waitBeweenTurn = 3000; //7 seconds
	
	public static boolean isPopUpShownInAutoMode = true;

	public static boolean isTournamentMode = false;

	public static boolean isThreadingForTournament = false;
}

package com.risk6441.strategy;

import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Human implements IStrategy {

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer) {
		
		if(currentPlayer.getArmies()>0)
    	{
    		if(territory == null) {
    			CommonMapUtil.alertBox("infor", "Please Select a territory.", "Alert");
    			return;
    		}
    		
    		int getArmy=CommonMapUtil.inputDialogueBoxForRenforcement();
    		
    		if(getArmy > 0) {
    			if(getArmy > currentPlayer.getArmies()) {
    				CommonMapUtil.alertBox("Info", "The Army to be moved in reinforce phase should be less than army you have.", "Alert");
    				return;
    			}else {
    				territory.setArmy(territory.getArmy() + getArmy);
    				currentPlayer.setArmies(currentPlayer.getArmies() - getArmy);
    				CommonMapUtil.enableOrDisableSave(false);
    				GameUtils.addTextToLog("==="+getArmy+" assigned to : === \n"+territory+"  -- Player "+currentPlayer.getName()+"\n", txtAreaMsg);
    				GameUtils.addTextToLog("======Reinforce Phase Completed. ===========\n", txtAreaMsg);
    			}
    		}else {
    			CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
    			return;
    		}
    	}
				
	}

}

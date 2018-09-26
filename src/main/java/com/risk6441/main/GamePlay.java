/**
 * 
 */
package com.risk6441.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.risk.map.util.MapFileParser;
import com.risk6441.maputils.MapReader;
import com.risk6441.maputils.MapUtils;


/**
 * @author Nirav
 *
 */
public class GamePlay implements ActionListener{

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		File file = MapUtils.showFileDialog();
		MapReader mapReader = new MapReader();

	}

	
}

/**
 * 
 */
package com.risk6441.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.risk6441.maputils.MapReaderBackup;
import com.risk6441.maputils.CommonMapUtil;


/**
 * @author Nirav
 *
 */
public class GamePlay implements ActionListener{

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e){
		File file = CommonMapUtil.showFileDialog();
		MapReaderBackup mapReader = new MapReaderBackup();

	}

	
}

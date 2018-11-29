package com.risk6441.controller;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.junit.Before;
import org.junit.Test;

public class SplashControllerTest {

	ClassLoader loader;

	@Before
	public void before() {
		loader = getClass().getClassLoader();
	}

	@Test
	public void testLoadGame() {
		SplashController ob = new SplashController();
		File file = new File(loader.getResource("saveGameController.game").getFile());
		System.out.println(file.getPath());
		try {
			ob.readSavedGame(file);
		} catch (Exception e) {
			
		}
	}
}

package com.risk6441.controller;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

public class SplashControllerTest {

	@Test
	public void testLoadGame()
	{
		SplashController ob = new SplashController();
		assertNotNull(ob.readSavedGame(new File("src\\main\\resources\\sampleLoadGame.game.game")));
	}
}

package com.risk6441.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk6441.controller.ControllerTestSuite;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.gameutils.GameUtilsTest;
import com.risk6441.gameutils.GameUtilsTestSuite;
import com.risk6441.maputils.MapUtilTestSuite;
import com.risk6441.models.ModelsTestSuite;
import com.risk6441.strategy.StrategyTestSuite;

/**
 * @author Nirav
 *
 */
@RunWith(Suite.class)
@SuiteClasses({MapUtilTestSuite.class,GameUtilsTestSuite.class, ModelsTestSuite.class, StrategyTestSuite.class,ControllerTestSuite.class})
public class MainTestSuite {
}



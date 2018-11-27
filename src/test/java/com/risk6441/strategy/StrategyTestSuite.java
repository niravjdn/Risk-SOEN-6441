package com.risk6441.strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Nirav
 */
@RunWith(Suite.class)
@SuiteClasses({AggressiveTest.class, RandomTest.class, BenevolentTest.class, CheaterTest.class})
public class StrategyTestSuite{
	
}



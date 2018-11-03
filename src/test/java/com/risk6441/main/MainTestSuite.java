package com.risk6441.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk6441.gameutils.GameUtilsTest;
import com.risk6441.gameutils.GameUtilsTestSuite;
import com.risk6441.maputils.MapUtilTestSuite;
import com.risk6441.models.ModelsTestSuite;

/**
 * @author Nirav
 *
 */
@RunWith(Suite.class)
@SuiteClasses({MapUtilTestSuite.class,GameUtilsTestSuite.class, ModelsTestSuite.class})
public class MainTestSuite {

}



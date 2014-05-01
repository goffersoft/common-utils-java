package com.goffersoft.common.utils;


import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ BitUtilsTest.class,
				EndianConversionTest.class })
public class CommonUtilsTestSuite {
	private static final Logger log = Logger.getLogger(CommonUtilsTestSuite.class);
}

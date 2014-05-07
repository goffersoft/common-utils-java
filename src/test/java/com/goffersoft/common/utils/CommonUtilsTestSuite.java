/**
 ** File: CommonUtilsTestSuite.java
 **
 ** Description : Entry For all Tests associated with the common.utils package
 **
 ** Date           Author                          Comments
 ** 02/12/2014     Prakash Easwar                  Created  
 */
package com.goffersoft.common.utils;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BitUtilsTest.class, EndianConversionTest.class })
public class CommonUtilsTestSuite {
    private static final Logger log = Logger
            .getLogger(CommonUtilsTestSuite.class);
}

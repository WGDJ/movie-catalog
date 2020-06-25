package com.wgdj.moviecatalog;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.wgdj.moviecatalog.integration.service.ServiceIntegrationTestSuite;
import com.wgdj.moviecatalog.unit.controller.ControllerUnitTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ ServiceIntegrationTestSuite.class, ControllerUnitTestSuite.class })
public class TestSuite {
}
package com.wgdj.moviecatalog.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.wgdj.moviecatalog.integration.service.ServiceIntegrationTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ ServiceIntegrationTestSuite.class })
public class IntegrationTestSuite {
}
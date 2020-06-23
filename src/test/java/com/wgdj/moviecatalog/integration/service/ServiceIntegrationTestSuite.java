package com.wgdj.moviecatalog.integration.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.wgdj.moviecatalog.integration.service.movie.MovieTest;

@RunWith(Suite.class)
@SuiteClasses({CollectionTest.class, MovieTest.class})
public class ServiceIntegrationTestSuite {}
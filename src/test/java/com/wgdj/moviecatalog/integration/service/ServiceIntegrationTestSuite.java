package com.wgdj.moviecatalog.integration.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.wgdj.moviecatalog.integration.service.movie.MovieServiceTest;


@RunWith(Suite.class)
@SuiteClasses({CollectionServiceTest.class, MovieServiceTest.class})
public class ServiceIntegrationTestSuite {}
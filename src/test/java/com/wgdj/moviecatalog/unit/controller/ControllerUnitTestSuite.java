package com.wgdj.moviecatalog.unit.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.wgdj.moviecatalog.unit.controller.movie.MovieControllerTest;


@RunWith(Suite.class)
@SuiteClasses({MovieControllerTest.class, CollectionControllerTest.class})
public class ControllerUnitTestSuite {}
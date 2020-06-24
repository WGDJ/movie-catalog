package com.wgdj.moviecatalog.integration.controller.movie;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.wgdj.moviecatalog.controller.CollectionController;

@RunWith(SpringRunner.class)
@WebMvcTest(CollectionController.class)
public class MovieControllerTest {
	
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private PopulatorControllerMovieTest populatorControllerMovieTest;
    
	@Before
	public void init() {
		populatorControllerMovieTest.clearMongoCollections();
	}

	@Test
	public void givenCollections_whenGetAllCollections_thenReturnJsonArray()
	  throws Exception {
	     
		String title1 = "Hannibal";
		populatorControllerMovieTest.createAnSaveMovieWithAllChilds(title1);
		String title2 = "Mad Max Collection";
		populatorControllerMovieTest.createAnSaveMovieWithAllChilds(title2);

		mvc.perform(get("/collections")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(2)))
			      .andExpect(jsonPath("$[0].title", is(title1)))
	    		  .andExpect(jsonPath("$[2].title", is(title2)));
	}
	

}

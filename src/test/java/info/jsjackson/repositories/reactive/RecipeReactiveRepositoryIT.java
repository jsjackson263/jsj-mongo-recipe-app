/**
 * 
 */
package info.jsjackson.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.jsjackson.domain.Recipe;

/**
 * @author jsjackson
 *
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;
	
	
	@Before
	public void setUp() throws Exception {
		recipeReactiveRepository.deleteAll().block();
	}

	@Test
	public void testRecipeSave() throws Exception {
		
		Recipe recipe = new Recipe();
		recipe.setDescription("Yummy Guac");
		
		recipeReactiveRepository.save(recipe).block();
		
		Long count = recipeReactiveRepository.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	}

}

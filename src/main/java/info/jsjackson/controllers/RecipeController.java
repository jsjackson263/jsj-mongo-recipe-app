/**
 * 
 */
package info.jsjackson.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.services.RecipeService;

/**
 * @author josan
 *
 */
@Controller
public class RecipeController {
	
	RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		Recipe recipe = recipeService.findById(new Long(id));
		model.addAttribute("recipe", recipe);
		
		//return view name
		return "recipe/show";
	}
	
	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}

	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		
		return "";
	}
	
	//@RequestMapping(name = "recipe", method = RequestMethod.POST)  - alternatively
	@PostMapping
	@RequestMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		//@ModelAttribute tells Spring to bind the form post parameters to RecipeCommand object
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		//tell SpringMVC to redirect to a specific url
		return "redirect:/recipe/" + savedCommand.getId() + "/show"; 
		
	}
}

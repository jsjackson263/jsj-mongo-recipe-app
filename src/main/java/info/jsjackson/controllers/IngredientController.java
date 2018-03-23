/**
 * 
 */
package info.jsjackson.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.services.IngredientService;
import info.jsjackson.services.RecipeService;
import info.jsjackson.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan
 *
 */
@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, 
			IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {

		log.debug("Getting ingredient list for recipe id:" + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		
		IngredientCommand ingredientCommand = 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		model.addAttribute("ingredient", ingredientCommand);
		
		return "recipe/ingredient/show";

	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		
		//make sure we have a good id value
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		//TODO: raise exception if null
		
		//need to turn back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);
		
		//init UOM
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
		
		
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateRecipeIngredient(
			@PathVariable String recipeId,
			@PathVariable String ingredientId,
			Model model) {
		
		IngredientCommand ingredientcommand = 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		model.addAttribute("ingredient", ingredientcommand);
		
		Set<UnitOfMeasureCommand> unitOfMeasureSet = unitOfMeasureService.listAllUoms();
		model.addAttribute("uomList", unitOfMeasureSet);
		
		
		return "recipe/ingredient/ingredientform";
		
	}
	
	@PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
	
	@GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		
		log.debug("In Controller - deleting Ingredient Id:  " + ingredientId + " for Recipe Id :" + recipeId);
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	

}

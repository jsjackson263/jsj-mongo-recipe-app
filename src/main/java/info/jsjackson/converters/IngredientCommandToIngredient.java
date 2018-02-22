/**
 * 
 */
package info.jsjackson.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.domain.Ingredient;
import lombok.Synchronized;

/**
 * @author josan 
 *
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure  uomConverter;
	
	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		this.uomConverter = uomConverter;
	}


	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand command) {
		if (command == null) {
			return null;
		}
		
		final Ingredient ingredient = new Ingredient();
		ingredient.setId(command.getId());
		ingredient.setDescription(command.getDescription());
		ingredient.setAmount(command.getAmount());
		ingredient.setUom(uomConverter.convert(command.getUnitOfMeasure()));
		
		return ingredient;
	}

}
;
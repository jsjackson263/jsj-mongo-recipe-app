/**
 * 
 */
package info.jsjackson.commands;

import java.math.BigDecimal;

import info.jsjackson.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author josan
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {

	private Long id;
	private Long recipeId;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasureCommand unitOfMeasure;
	
	
}

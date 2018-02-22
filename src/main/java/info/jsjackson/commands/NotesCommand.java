/**
 * 
 */
package info.jsjackson.commands;

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
public class NotesCommand {

	private Long id;
	private String recipeNotes;
	
}

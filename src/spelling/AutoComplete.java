/**
 * 
 */
package spelling;

import java.util.List;

/**
 * @author Anvesh
 *
 */
public interface AutoComplete {
	public List<String> predictCompletions(String prefix, int numCompletions);
}

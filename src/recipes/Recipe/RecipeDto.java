package recipes.Recipe;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public record RecipeDto (@NotBlank String name,
                         @NotBlank String description,
                         @NotBlank String category,
                         LocalDateTime date,
                         @NotEmpty List<String> ingredients,
                         @NotEmpty List<String> directions) {

    public RecipeDto(Recipe entity) {
        this(entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getDate(),
                entity.getIngredients(),
                entity.getDirections());
    }
}

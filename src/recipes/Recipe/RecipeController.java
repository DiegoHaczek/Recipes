package recipes.Recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.Utils.RequestParameters;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("recipe/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable int id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping("recipe/search")
    public ResponseEntity<List<RecipeDto>> getAllByParam(@RequestParam(name = "category", required = false) String category,
                                                         @RequestParam(name = "name", required = false) String name) {
        return recipeService.getByParam(new RequestParameters(name,category));
    }

    @PostMapping("recipe/new")
    public ResponseEntity<?> createRecipe(@RequestBody @Valid RecipeDto recipe) {
        return recipeService.createRecipe(recipe);
    }

    @DeleteMapping("recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
        return recipeService.deleteRecipe(id);
    }

    @PutMapping("recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable int id, @RequestBody @Valid RecipeDto dto) {
        return recipeService.updateRecipe(id,dto);
    }
}

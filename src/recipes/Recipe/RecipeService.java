package recipes.Recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.User.User;
import recipes.Utils.RequestParameters;


import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private void checkIfRecipeExists(int id) {
        if (!recipeRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Recipe not found");
        }
    }

    public ResponseEntity<RecipeDto> getRecipe(int id) {
        checkIfRecipeExists(id);
        RecipeDto response = new RecipeDto(recipeRepository.getById(id));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> createRecipe(RecipeDto dto) {
        Recipe recipe = new Recipe(dto);
        recipe.setUser(getCurrentUser());
        recipeRepository.save(recipe);
        return ResponseEntity.ok(Map.of("id",recipe.getId()));
    }

    public ResponseEntity<?> deleteRecipe(int id) {
        recipeRepository.findById(id).ifPresentOrElse(this::checkIfIsRecipeOwner,
                this::throwNotFoundException);

        recipeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> updateRecipe(int id, RecipeDto dto) {
        recipeRepository.findById(id).ifPresentOrElse(this::checkIfIsRecipeOwner,
                this::throwNotFoundException);
        Recipe recipe = new Recipe(dto);
        recipe.setId(id);
        recipeRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<Recipe> getByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> getByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public ResponseEntity<List<RecipeDto>> MapToDto(List<Recipe> entityList) {
        return ResponseEntity.ok(
                entityList.stream()
                .map(RecipeDto::new)
                .toList());
    }

    public ResponseEntity<List<RecipeDto>> getByParam(RequestParameters params) {
        if (params.getCategory().isPresent() && params.getName().isPresent() ||
            params.getCategory().isEmpty() && params.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only one parameter is needed");
        }
        return (params.getCategory().isPresent()) ?
                MapToDto(getByCategory(params.category())) :
                MapToDto(getByName(params.name()));
    }

    private static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    private void checkIfIsRecipeOwner(Recipe recipe) {
        System.out.println(recipe.getUserId());
        if (recipe.getUserId() != getCurrentUser().getId()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "");
            }
        }

    private void throwNotFoundException(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"");
    }
}

package recipes.Utils;

import java.util.Optional;

public record RequestParameters(String name, String category) {
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getCategory() {
        return Optional.ofNullable(category);
    }
}

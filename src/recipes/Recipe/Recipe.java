package recipes.Recipe;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import recipes.User.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Entity
@Table
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "DATE")
    private LocalDateTime date;

    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> directions;

    @ManyToOne
    private User user;


    public Recipe(RecipeDto dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.category = dto.category();
        this.ingredients = dto.ingredients();
        this.directions = dto.directions();
        this.date = LocalDateTime.now();
    }

    public Recipe() {
    }

    public int getUserId(){
        return user.getId();
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
}

package recipes.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String email);

    Optional<User> findByUsername(String username);
}

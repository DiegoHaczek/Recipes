package recipes.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    private String role;

    @ElementCollection
    private final List<GrantedAuthority> rolesAndAuthorities;

    public User(User user) {
        username = user.getUsername();
        password = user.getPassword();
        id = user.getId();
        role = user.getRole();
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));}

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.role = "ROLE_USER";
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public User() {
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

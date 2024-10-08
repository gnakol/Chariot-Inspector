package fr_scapartois_auto.chariot_inspector.account.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import fr_scapartois_auto.chariot_inspector.role.beans.Role;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long idAccount;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "civility")
    private String civility;



    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "id_role", nullable = false)
    @JoinTable(name = "role_account",
            joinColumns = @JoinColumn(name = "id_account"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"account"})
    private List<Issue> issues;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"account", "issue"})
    private List<ActionCarriedOut> actionCarriedOuts;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties({"account", "taurus"})
    private List<TaurusUsage> taurusUsages;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties({"account", "cart"})
    private List<Pickup> pickups;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_service")
    private AccountServiceBean accountServiceBean;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();

        this.roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" +role.getRoleName()));
            authorities.addAll(role.getAuthorities());
        });

        return authorities;
    }


    @Override
    public String getUsername() {
        return this.getEmail();
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

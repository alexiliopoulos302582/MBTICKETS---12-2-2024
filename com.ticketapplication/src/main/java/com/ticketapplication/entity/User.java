package com.ticketapplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    public String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    private String enabled;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinTable(name="users_roles",
    joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
        private Collection<Role> role;

    public User(String password, String email, Collection<? extends GrantedAuthority>grantedAuthorities) {

    }

    public User() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;    }

    @Override
    public String getUsername() {
        return userName;    }

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

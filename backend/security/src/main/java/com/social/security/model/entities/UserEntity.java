package com.social.security.model.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.social.security.model.dto.UserDTO;
import com.social.security.utility.DateUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Mono;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity extends MainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id")
    private CredentialEntity credentials;

    @ManyToMany
    @JoinTable(
        name = "USERS_PROFILES",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<ProfileEntity> profiles = new HashSet<>();

    /* domain methods */

    public static Mono<UserEntity> createUserEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(DateUtils.parseDate(userDTO.getDateOfBirth()));
        return Mono.just(user);
    }

    public Mono<Void> updateUserEntity(UserDTO userDTO) {
        return Mono.fromRunnable(() -> {
            this.fullName = userDTO.getFullName();
            this.email = userDTO.getEmail();
            this.gender = userDTO.getGender();
            this.dateOfBirth = DateUtils.parseDate(userDTO.getDateOfBirth());
        });
    }
    
}

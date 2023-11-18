package com.social.security.model.entities;

import com.social.security.model.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Mono;


@Entity
@Table(name = "CREDENTIALS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CredentialEntity extends MainEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    public static Mono<CredentialEntity> createCredentials(UserDTO dto) {
        CredentialEntity credentials = new CredentialEntity();
        credentials.setUserName(dto.getUserName());
        credentials.setPassword(dto.getPassword());
        return Mono.just(credentials);
    }

}

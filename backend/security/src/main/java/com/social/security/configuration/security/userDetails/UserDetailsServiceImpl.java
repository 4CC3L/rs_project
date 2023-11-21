package com.social.security.configuration.security.userDetails;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.security.common.RsSecurityProperties;
import com.social.security.model.entities.ProfileEntity;
import com.social.security.model.entities.UserEntity;
import com.social.security.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RsSecurityProperties properties;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
/*         // TODO Auto-generated method stub
        UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException(MessageFormat.format(properties.getUsernameNotFoundMessage(), username)));
        UserDetails userDetails = User
        .withUsername(user.getCredentials().getUserName())
        .password(passwordEncoder.encode(user.getCredentials().getPassword()))
        .build();
        return Mono.just(userDetails); */
        return Mono.just(username)
            .flatMap(name ->
                Mono.justOrEmpty(userRepository.findByUsername(name))
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException(
                                    MessageFormat.format(properties.getUsernameNotFoundMessage(), name))))
            )
            .flatMap(user ->
                    getAuthorities(user)
                        .collectList()
                        .map(authorities ->
                            User.builder()
                                .username(user.getCredentials().getUserName())
                                .password(passwordEncoder.encode(user.getCredentials().getPassword()))
                                .authorities(authorities)
                                .build()
                            )
            );
    }

    private Flux<GrantedAuthority> getAuthorities(UserEntity user) {
        /* Set<ProfileEntity> userProfiles = user.getProfiles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userProfiles.size());
        userProfiles.forEach(pro -> {
            authorities.add(new SimpleGrantedAuthority(pro.getName()));
        });
        return Flux.fromIterable(authorities); */
        return Flux.fromIterable(user.getProfiles())
            .map(ProfileEntity::getName)
            .map(SimpleGrantedAuthority::new);
    }

}

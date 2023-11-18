package com.social.security.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.social.security.config.RsSecurityProperties;
import com.social.security.model.dto.UserDTO;
import com.social.security.model.dto.UserInfoDTO;
import com.social.security.model.entities.CredentialEntity;
import com.social.security.model.entities.UserEntity;
import com.social.security.repository.UserRepository;
import com.social.security.service.UserService;
import com.social.security.utility.DateUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RsSecurityProperties properties;

    public UserServiceImpl(UserRepository userRepository, RsSecurityProperties properties) {
        this.userRepository = userRepository;
        this.properties = properties;
    }

    @Override
    public Flux<UserInfoDTO> getAllUsers() {
        return this.findAllR()
        .flatMapMany(Flux::fromIterable)
        .map(foundUser -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setFullName(foundUser.getFullName());
            userInfoDTO.setEmail(foundUser.getEmail());
            userInfoDTO.setGender(foundUser.getGender());
            userInfoDTO.setDateOfBirth(DateUtils.parseDateToString(foundUser.getDateOfBirth()));
            userInfoDTO.setUserName(foundUser.getCredentials().getUserName());
            return userInfoDTO;
        });
    }

    @Override
    public Mono<UserInfoDTO> createUser(UserDTO dto) {
        return UserEntity.createUserEntity(dto)
        .flatMap(user -> CredentialEntity.createCredentials(dto)
                .flatMap(cred -> {
                    user.setCredentials(cred);
                    return this.createUserR(user);
                })
                .map(savedUser -> {
                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setFullName(savedUser.getFullName());
                    userInfoDTO.setEmail(savedUser.getEmail());
                    userInfoDTO.setGender(savedUser.getGender());
                    userInfoDTO.setDateOfBirth(DateUtils.parseDateToString(savedUser.getDateOfBirth()));
                    userInfoDTO.setUserName(savedUser.getCredentials().getUserName());
                    return userInfoDTO;
                })
        );
    }

    @Override
    public Mono<UserInfoDTO> getUserById(Long userId) {
        return this.getUserByIdR(userId)
        .switchIfEmpty(Mono.error(new RuntimeException(MessageFormat.format(properties.getUserNotFoundMessage(), userId))))
        .map(foundUser -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setFullName(foundUser.getFullName());
            userInfoDTO.setEmail(foundUser.getEmail());
            userInfoDTO.setGender(foundUser.getGender());
            userInfoDTO.setDateOfBirth(DateUtils.parseDateToString(foundUser.getDateOfBirth()));
            userInfoDTO.setUserName(foundUser.getCredentials().getUserName());
            return userInfoDTO;
        });
    }

    @Override
    public Mono<UserInfoDTO> getUserByUsername(String username) {
        return this.getUserByUsernameR(username)
        .switchIfEmpty(Mono.error(new RuntimeException(MessageFormat.format(properties.getUsernameNotFoundMessage(), username))))
        .map(foundUser -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setFullName(foundUser.getFullName());
            userInfoDTO.setEmail(foundUser.getEmail());
            userInfoDTO.setGender(foundUser.getGender());
            userInfoDTO.setDateOfBirth(DateUtils.parseDateToString(foundUser.getDateOfBirth()));
            userInfoDTO.setUserName(foundUser.getCredentials().getUserName());
            return userInfoDTO;
        });
    }

    @Override
    public Mono<Void> updateUser(UserDTO user, Long userId) {
        return UserEntity.createUserEntity(user).flatMap(el -> {
            return this.updateUserR(el, userId);
        });
    }

    @Override
    public Mono<Void> deleteUser(Long userId) {
        return this.deleteUserR(userId);
    }

    private Mono<List<UserEntity>> findAllR() {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return userRepository.findAll();
        }));
    }

    private Mono<UserEntity> createUserR(UserEntity user) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return userRepository.save(user);
        }));
    }

    private Mono<UserEntity> getUserByIdR(Long id) {
        return Mono.fromCallable(() -> {
            return userRepository.findById(id)
            .orElse(null);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<UserEntity> getUserByUsernameR(String username) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {    
            return userRepository.findByUsername(username).orElse(null);
        }));
    }
    
    private Mono<Void> updateUserR(UserEntity user, Long userId) {
        return Mono.defer(() -> {
            boolean userExists = userRepository.existsById(userId);
            if (userExists) {
                userRepository.save(user);
            } else {
                return Mono.empty();
            }
            return Mono.empty();
        });
    }
    
    private Mono<Void> deleteUserR(Long userId) {
        return Mono.defer(() -> {
            boolean userExists = userRepository.existsById(userId);
            if (userExists) {
                userRepository.deleteById(userId);
            } else {
                return Mono.empty();
            }
            return Mono.empty();
        });
    }
    
}

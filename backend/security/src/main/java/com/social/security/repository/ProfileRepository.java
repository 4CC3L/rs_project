package com.social.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.security.model.entities.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    
}

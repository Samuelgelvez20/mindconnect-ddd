package com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.contact.emailcontact.adapters.out.persistence.entity.EmailContactJpaEntity;

public interface EmailContactJpaRepository extends JpaRepository<EmailContactJpaEntity, UUID> {
}
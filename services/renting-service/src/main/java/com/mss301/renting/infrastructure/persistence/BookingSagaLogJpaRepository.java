package com.mss301.renting.infrastructure.persistence;

import com.mss301.renting.domain.entity.BookingSagaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingSagaLogJpaRepository extends JpaRepository<BookingSagaLog, UUID> {
}

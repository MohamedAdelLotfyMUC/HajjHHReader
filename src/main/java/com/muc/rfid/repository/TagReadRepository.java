package com.muc.rfid.repository;

import com.muc.rfid.entity.TagRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TagReadRepository extends JpaRepository<TagRead, Long> {

    boolean existsByEpcAndReaderIdAndCreatedAtAfter(String epc, String readerId, LocalDateTime dateTime);

    @Query("SELECT t FROM TagRead t WHERE " +
            "(:epc IS NULL OR t.epc LIKE %:epc%) AND " +
            "(:readerId IS NULL OR t.readerId = :readerId)")
    Page<TagRead> findByFilters(@Param("epc") String epc,
                                @Param("readerId") String readerId,
                                Pageable pageable);

    Optional<TagRead> findFirstByOrderByCreatedAtDesc();

    Optional<TagRead> findFirstByEpcAndReaderIdOrderByCreatedAtDesc(String epc, String readerId);
}
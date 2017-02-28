package com.dtreb.repository;

import com.dtreb.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * {@link Authority} repository.
 *
 * @author dtreb
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query
    Authority findByName(String name);
}

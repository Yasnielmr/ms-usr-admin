package com.oriontek.ms_usr.repositories;

import com.oriontek.ms_usr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
public interface UserRepository extends JpaRepository<User, Long> {
}

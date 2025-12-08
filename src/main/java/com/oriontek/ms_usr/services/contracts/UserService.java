package com.oriontek.ms_usr.services.contracts;

import com.oriontek.ms_usr.dtos.UserDto;
import com.oriontek.ms_usr.dtos.UserRequest;

/**
 * UserService.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
public interface UserService {

    /**
     * Metodo para crear un nuevo usuario.
     *
     * @param userRequest {@link UserRequest}
     * @return {@link UserDto}
     */
    UserDto createUser(UserRequest userRequest);
}

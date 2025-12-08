package com.oriontek.ms_usr.services.contracts;

import com.oriontek.ms_usr.dtos.UserDto;
import com.oriontek.ms_usr.dtos.UserRequest;

import java.util.List;

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

    /**
     * Metodo para buscar el usuario en la base de datos segun su ID.
     *
     * @param id {@link Long}
     * @return {@link UserDto}
     */
    UserDto getUserById(Long id);

    /**
     * Metodo para mostrar un listado de usuarios.
     *
     * @return list {@link UserDto}
     */
    List<UserDto> getUsers();

    /**
     * Metodo para eliminar un usuario de la base de datos.
     *
     * @param id {@link Long}
     */
    void deleteUser(Long id);
}

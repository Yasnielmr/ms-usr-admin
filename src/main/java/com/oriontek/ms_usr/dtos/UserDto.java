package com.oriontek.ms_usr.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * UserDto.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
public record UserDto(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        List<AddressDto> address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements Serializable {
}

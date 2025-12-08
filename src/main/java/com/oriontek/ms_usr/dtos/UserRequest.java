package com.oriontek.ms_usr.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * Response.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
public record UserRequest(
        String name,
        String lastName,
        @Email
        @NotBlank
        String email,
        String phone,
        List<AddressDto> address
) implements Serializable {
}

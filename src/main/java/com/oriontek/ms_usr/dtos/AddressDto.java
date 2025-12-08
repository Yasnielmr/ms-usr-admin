package com.oriontek.ms_usr.dtos;

import java.io.Serializable;

/**
 * AddressDto.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
public record AddressDto (
        String street,
        String sector,
        String postalCode,
        String city
) implements Serializable{
}

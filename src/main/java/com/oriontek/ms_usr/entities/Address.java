package com.oriontek.ms_usr.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Address.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street;
    private String sector;
    private String postalCode;
    private String city;
}

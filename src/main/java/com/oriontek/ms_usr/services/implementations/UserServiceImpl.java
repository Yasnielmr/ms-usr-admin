package com.oriontek.ms_usr.services.implementations;

import com.oriontek.ms_usr.dtos.AddressDto;
import com.oriontek.ms_usr.dtos.UserDto;
import com.oriontek.ms_usr.dtos.UserRequest;
import com.oriontek.ms_usr.entities.Address;
import com.oriontek.ms_usr.entities.User;
import com.oriontek.ms_usr.repositories.UserRepository;
import com.oriontek.ms_usr.services.contracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * UserServiceImpl.
 *
 * @author Yasniel Montano.
 * @version 1.0.0, 07-12-2025
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Value("${ms.usr.emailRegex}")
    private String emailRegex;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto createUser(UserRequest userRequest) {

        final var pattern = Pattern.compile(emailRegex);
        final var matcher = pattern.matcher(userRequest.email());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Formato de correo invalido");
        }
        final var user = this.userRepository.save(this.getUserEntity(userRequest));
        return this.getUserResponse(user);
    }

    /**
     * Metodo para registrar el usuario en la base de datos.
     *
     * @param userRequest {@link UserRequest}
     * @return {@link User}
     */
    private User getUserEntity(UserRequest userRequest) {

        final var user = new User();
        user.setName(userRequest.name());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPhone(userRequest.phone());
        user.setAddress(userRequest.address().stream().map(a -> {
            final var address = new Address();
            address.setCity(a.city());
            address.setPostalCode(a.postalCode());
            address.setStreet(a.street());
            address.setSector(a.sector());
            return address;
        }).collect(Collectors.toList()));
        return user;
    }

    /**
     * Metodo para transformar el objeto user de la base de datos en un response.
     *
     * @param userEntity {@link User}
     * @return {@link UserDto}
     */
    private UserDto getUserResponse(User userEntity) {

        return new UserDto(
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                this.mapAddressList(userEntity.getAddress()),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    /**
     * Metodo para mapear una direccion de la base datos.
     *
     * @param address {@link Address}
     * @return {@link AddressDto}
     */
    private AddressDto getAddress(Address address) {

        return new AddressDto(
                address.getStreet(),
                address.getSector(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    /**
     * Metodo para mapear un listado de direcciones de la base datos.
     *
     * @param addresses {@link Address}
     * @return list {@link AddressDto}
     */
    private List<AddressDto> mapAddressList(List<Address> addresses) {

        if (addresses == null) return List.of();
        return addresses.stream()
                .map(this::getAddress)
                .toList();
    }

}

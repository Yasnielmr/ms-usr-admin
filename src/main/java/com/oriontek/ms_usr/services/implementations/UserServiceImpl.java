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
import java.util.NoSuchElementException;
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

        this.validateEmail(userRequest.email());
        final var user = this.userRepository.save(this.getUserEntity(userRequest));
        return this.getUserResponse(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto getUserById(Long id) {

        final var user = this.userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado en la base de datos"));
        return this.getUserResponse(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> getUsers() {
        return this.userRepository.findAll()
                .stream().map(this::getUserResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Usuario no encontrado en la base de datos para ser eliminado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(Long id, UserRequest request) {

        validateEmail(request.email());

        final var user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado en la base de datos"));

        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        request.address().forEach(a -> {
            final var address = new Address();
            address.setCity(a.city());
            address.setPostalCode(a.postalCode());
            address.setStreet(a.street());
            address.setSector(a.sector());
        });
        final var updated = userRepository.save(user);
        return this.getUserResponse(updated);
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
                userEntity.getId(),
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
        return new AddressDto(address.getStreet(), address.getPostalCode(), address.getCity(), address.getSector());
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

    /**
     * Metodo para validar el email del usuario.
     *
     * @param email {@link String}
     */
    private void validateEmail(String email) {
        final var pattern = Pattern.compile(emailRegex);
        final var matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Formato de correo invalido");
        }
    }
}

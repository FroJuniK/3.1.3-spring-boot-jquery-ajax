package UserManagement.service;

import UserManagement.dto.UserDto;
import UserManagement.model.Role;
import UserManagement.repository.RoleRepository;
import UserManagement.repository.UserRepository;
import UserManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(getDtoFromUser(user));
        }
        return users;
    }

    public UserDto getUserById(Long id) {
        return getDtoFromUser(userRepository.findById(id).get());
    }

    public UserDto getUserByName(String name) {
        return getDtoFromUser(userRepository.findByName(name));
    }

    public void addUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(getUserFromDto(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void editUser(UserDto user) {
        User userUpd = userRepository.findById(user.getId()).get();
        userUpd.setPassword(passwordEncoder.encode(user.getPassword()));
        userUpd.setRoles(getSetOfRoles(user.getRoles()));
        userUpd.setName(user.getName());
        userUpd.setLastName(user.getLastName());
        userRepository.save(userUpd);
    }

    public Set<String> getNameRoles() {
        Set<String> nameRoles = new HashSet<>();
        for (Role role : roleRepository.findAll()) {
            nameRoles.add(role.getRole());
        }
        return nameRoles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }

    private User getUserFromDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getLastName(),
                userDto.getEmail(), userDto.getPassword(), getSetOfRoles(userDto.getRoles()));
    }

    private UserDto getDtoFromUser(User user) {
        return new UserDto(user.getId(), user.getName(), user.getLastName(),
                user.getEmail(), user.getPassword(), getSetOfString(user.getRoles()));
    }

    private Set<Role> getSetOfRoles(Set<String> nameRoles) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : nameRoles) {
            roles.add(roleRepository.findByRole(roleName));
        }
        return roles;
    }

    public Set<String> getSetOfString(Set<Role> roles) {
        Set<String> nameRoles = new HashSet<>();
        for (Role role : roles) {
            nameRoles.add(role.getRole());
        }
        return nameRoles;
    }
}

package UserManagement.service;

import UserManagement.dto.UserDto;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto getUserByName(String name);

    void addUser(UserDto user);

    void deleteUser(Long id);

    void editUser(UserDto user);

    Set<String> getNameRoles();
}

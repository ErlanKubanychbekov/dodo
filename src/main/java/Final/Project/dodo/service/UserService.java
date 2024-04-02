package Final.Project.dodo.service;

import Final.Project.dodo.base.BaseService;
import Final.Project.dodo.model.dto.CategoriesDto;
import Final.Project.dodo.model.dto.UserDto;
import Final.Project.dodo.model.request.create.CategoriesCreateRequest;
import Final.Project.dodo.model.request.create.UserCreateRequest;
import Final.Project.dodo.model.request.update.CategoriesUpdateRequest;
import Final.Project.dodo.model.request.update.UserUpdateRequest;
import Final.Project.dodo.model.response.UserResponse;

public interface UserService extends BaseService<UserDto> {
    UserDto create(UserCreateRequest request);


    UserDto update(UserUpdateRequest request);
    UserDto updated(UserDto request);

    Boolean delete(Long id);
    UserResponse getUserByEmail(String email);
    UserDto getUserByToken(String token);
}

package Final.Project.dodo.service.impl;

import Final.Project.dodo.authservice.utils.JwtProvider;
import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.UserRep;
import Final.Project.dodo.model.dto.UserDto;
import Final.Project.dodo.model.entities.User;
import Final.Project.dodo.model.mapper.UserMapper;
import Final.Project.dodo.model.request.create.UserCreateRequest;
import Final.Project.dodo.model.request.update.UserUpdateRequest;
import Final.Project.dodo.model.response.UserResponse;
import Final.Project.dodo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserRep, UserDto, UserMapper> implements UserService {
    public UserServiceImpl(UserRep rep, UserMapper mapper, JwtProvider jwtProvider) {
        super(rep, mapper);
        this.jwtProvider = jwtProvider;
    }
    private final JwtProvider jwtProvider;

    @Override
    public UserDto getUserByToken(String token) {
        return findById(jwtProvider.validateToken(token));
    }

    @Override
    public UserDto create(UserCreateRequest request) {
        UserDto dto = new UserDto();
        dto.setName(request.getName());
        dto.setEmail(request.getEmail());
        dto.setPhone(request.getPhone());
        dto.setDodoCoins(request.getDodoCoins());
        dto.setTempPass(request.getTempPass());
        dto.setSendDate(request.getSendDate());
        return save(dto);


    }

    @Override
    public UserDto update(UserUpdateRequest request) {
        UserDto userDto = new UserDto();
        userDto.setId(request.getId());
        userDto.setName(request.getName());
        userDto.setEmail(request.getEmail());
        userDto.setPhone(request.getPhone());
        userDto.setDodoCoins(request.getDodoCoins());
        userDto.setTempPass(request.getTempPass());
        userDto.setSendDate(request.getSendDate());
        return update(userDto);
    }


    @Override
    public UserDto updated(UserDto request) {
        UserDto dto = new UserDto();
        dto.setId(request.getId());
        dto.setName(request.getName());
        dto.setEmail(request.getEmail());
        dto.setPhone(request.getPhone());
        dto.setDodoCoins(request.getDodoCoins());
        dto.setTempPass(request.getTempPass());
        dto.setSendDate(request.getSendDate());
        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return rep.findUserByEmail(email);
    }
}

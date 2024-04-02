package Final.Project.dodo.dao;

import Final.Project.dodo.base.BaseRep;
import Final.Project.dodo.model.dto.UserDto;
import Final.Project.dodo.model.entities.User;
import Final.Project.dodo.model.response.UserResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends BaseRep<User> {
    @Query( value = "select * from tb_user u where u.email= :email",nativeQuery = true)
    UserResponse findUserByEmail(String email);
}

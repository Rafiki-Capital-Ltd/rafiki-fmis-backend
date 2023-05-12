package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.dto.user.CreateUserDto;
import ke.co.rafiki.fmis.dto.user.GetUserDto;
import ke.co.rafiki.fmis.dto.user.UpdateUserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    GetUserDto toGetUserDto(User user);

    User toUser(CreateUserDto createUserDto);

    User toUser(UpdateUserDto updateUserDto);
}

package ke.co.rafiki.fmis.dto.mapper;

import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.dto.user.CreateUserDto;
import ke.co.rafiki.fmis.dto.user.GetUserDto;
import ke.co.rafiki.fmis.dto.user.UpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    GetUserDto userToGetUserDto(User user);
    
    User createUserDtoToUser(CreateUserDto createUserDto);

    User updateUserDtoToUser(UpdateUserDto updateUserDto);
}

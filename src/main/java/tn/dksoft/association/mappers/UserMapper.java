package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.UserDTO;
import tn.dksoft.association.entity.User;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends GenericMapper<UserDTO, User> {

	UserDTO fromEntityToDto(User user);

	User fromDtoToEntity(UserDTO user);

}

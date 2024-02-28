package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.RoomDTO;
import tn.dksoft.association.entity.Room;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoomMapper extends GenericMapper<RoomDTO, Room> {

}

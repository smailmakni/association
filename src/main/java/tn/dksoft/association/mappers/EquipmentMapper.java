package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.EquipmentDTO;
import tn.dksoft.association.entity.Equipment;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EquipmentMapper extends GenericMapper<EquipmentDTO, Equipment> {

}

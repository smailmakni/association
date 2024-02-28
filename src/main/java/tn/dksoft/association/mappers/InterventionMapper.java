package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.InterventionDTO;
import tn.dksoft.association.entity.Intervention;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InterventionMapper extends GenericMapper<InterventionDTO, Intervention> {

}

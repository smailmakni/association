package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.CenterDTO;
import tn.dksoft.association.entity.Center;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CenterMapper extends GenericMapper<CenterDTO, Center> {

}

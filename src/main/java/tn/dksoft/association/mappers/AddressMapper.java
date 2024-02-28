package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.AddressDTO;
import tn.dksoft.association.entity.Address;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper extends GenericMapper<AddressDTO, Address> {

}

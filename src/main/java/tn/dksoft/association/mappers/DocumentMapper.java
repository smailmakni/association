package tn.dksoft.association.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.entity.Document;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DocumentMapper extends GenericMapper<DocumentDTO, Document> {

}

package it.soriani.tefo.mapper;

import org.mapstruct.factory.Mappers;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */
public interface GenericMapper<E, D> {

    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);

    D entityToDto(E entity);

    E dtoToEntity(D dto);

}

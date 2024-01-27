package it.soriani.tefo.mapper;

import it.soriani.tefo.dto.model.ContactsDTO;
import it.soriani.tefo.entity.Contacts;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Mapper(componentModel = "spring")
public interface ContactsMapper extends GenericMapper<Contacts, ContactsDTO> {

    List<ContactsDTO> entityListToDTOList(List<Contacts> contactsList);

}

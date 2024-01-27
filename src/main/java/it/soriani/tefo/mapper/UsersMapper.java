package it.soriani.tefo.mapper;

import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.entity.Users;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Mapper(componentModel = "spring", uses = {ContactsMapper.class})
public interface UsersMapper extends GenericMapper<Users, UsersDTO> {

    List<UsersDTO> entityListToDtoList(List<Users> entityList);


}

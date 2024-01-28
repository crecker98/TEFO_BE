package it.soriani.tefo.mapper;

import it.soriani.tefo.dto.model.MessagesDTO;
import it.soriani.tefo.entity.Messages;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Mapper(componentModel = "spring", uses = {UsersMapper.class})
public interface MessagesMapper extends GenericMapper<Messages, MessagesDTO> {

    List<MessagesDTO> entityListToDtoList(List<Messages> entityList);

}

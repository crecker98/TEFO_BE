package it.soriani.tefo.mapper;

import it.soriani.tefo.dto.model.ChatsDTO;
import it.soriani.tefo.entity.Chats;
import org.mapstruct.Mapper;

/**
 * @author christiansoriani on 02/02/24
 * @project TEFO_BE
 */

@Mapper(componentModel = "spring")
public interface ChatsMapper extends GenericMapper<Chats, ChatsDTO> {
}

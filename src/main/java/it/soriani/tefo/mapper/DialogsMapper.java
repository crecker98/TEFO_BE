package it.soriani.tefo.mapper;

import it.soriani.tefo.dto.model.DialogsDTO;
import it.soriani.tefo.entity.Dialogs;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Mapper(componentModel = "spring")
public interface DialogsMapper extends GenericMapper<Dialogs, DialogsDTO> {

    List<DialogsDTO> entityListToDtoList(List<Dialogs> entityList);

}

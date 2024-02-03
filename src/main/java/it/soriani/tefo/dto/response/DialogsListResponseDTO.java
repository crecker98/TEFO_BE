package it.soriani.tefo.dto.response;

import it.soriani.tefo.dto.common.GenericResponseDTO;
import it.soriani.tefo.dto.model.DialogsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DialogsListResponseDTO extends GenericResponseDTO<List<DialogsDTO>> {
}

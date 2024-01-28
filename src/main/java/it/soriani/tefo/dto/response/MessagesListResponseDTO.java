package it.soriani.tefo.dto.response;

import it.soriani.tefo.dto.model.MessagesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessagesListResponseDTO extends GenericResponseDTO<List<MessagesDTO>> {
}

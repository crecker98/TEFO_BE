package it.soriani.tefo.dto.response;

import it.soriani.tefo.dto.common.GenericPaginationResponseDTO;
import it.soriani.tefo.dto.model.ChatsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author christiansoriani on 02/02/24
 * @project TEFO_BE
 */

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ChatsPaginatedResponseDTO extends GenericPaginationResponseDTO<List<ChatsDTO>> {
}

package it.soriani.tefo.dto.response;

import it.soriani.tefo.dto.model.UsersDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UsersListResponseDTO extends GenericResponseDTO<List<UsersDTO>> {
}

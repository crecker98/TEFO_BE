package it.soriani.tefo.dto.response;

import it.soriani.tefo.dto.model.ContactsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */


@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContactsListResponseDTO extends GenericResponseDTO<List<ContactsDTO>> {
}

package it.soriani.tefo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author christiansoriani on 23/01/24
 * @project TEFO_BE
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
public class ContactsDTO extends BaseDTO {

    private int uid;

    private boolean mutual;

}

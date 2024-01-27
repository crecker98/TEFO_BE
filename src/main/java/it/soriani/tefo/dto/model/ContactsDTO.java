package it.soriani.tefo.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 23/01/24
 * @project TEFO_BE
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsDTO {

    private Integer uid;

    private int mutual;

}

package it.soriani.tefo.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    private int uid;

    private String name;

    private Integer status;

    private byte[] data;

    private ContactsDTO contact;

}

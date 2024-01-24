package it.soriani.tefo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author christiansoriani on 23/01/24
 * @project TEFO_BE
 */


@Getter
@AllArgsConstructor
public enum TableEnums {

    CONTACTS("contacts"),
    USERS("user");

    private final String name;

}

package it.soriani.tefo.dto.model;

import it.soriani.tefo.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessagesDTO {

    private Integer mid;

    private Integer uid;

    private Integer readState;

    private Integer sendState;

    private Integer date;

    private byte[] data;

    private Integer out;

    private Users users;

}

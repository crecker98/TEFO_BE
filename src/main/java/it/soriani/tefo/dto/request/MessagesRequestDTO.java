package it.soriani.tefo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 05/02/24
 * @project TEFO_BE
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagesRequestDTO {

    private String namSurnameUser;
    private Boolean isSent;
    private Boolean isDelivered;
    private Boolean isRead;
    private Boolean hasMedia;

}

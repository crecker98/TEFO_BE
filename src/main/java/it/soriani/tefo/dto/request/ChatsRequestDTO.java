package it.soriani.tefo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 03/02/24
 * @project TEFO_BE
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatsRequestDTO {

    private String title;
    private Integer numberOfParticipants;
    private Boolean left;

}

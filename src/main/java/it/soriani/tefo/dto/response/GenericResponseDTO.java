package it.soriani.tefo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericResponseDTO<T> {

    private T payload;

}

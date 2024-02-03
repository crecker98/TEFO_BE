package it.soriani.tefo.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author christiansoriani on 31/01/24
 * @project TEFO_BE
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericPaginationResponseDTO<T> {

    private PageDTO page;

    private T payload;

}

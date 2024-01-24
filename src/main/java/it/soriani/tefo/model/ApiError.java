package it.soriani.tefo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Data
@AllArgsConstructor
@Builder
public class ApiError {

    private String messageCode;

    private String description;

}

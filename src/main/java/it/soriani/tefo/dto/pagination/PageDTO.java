package it.soriani.tefo.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 31/01/24
 * @project TEFO_BE
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO {

    private int size;
    private Long totalElements;
    private int totalPages;
    private int number;
    private String sortedBy;

}

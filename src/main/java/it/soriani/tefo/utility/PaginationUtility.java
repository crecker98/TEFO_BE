package it.soriani.tefo.utility;

import it.soriani.tefo.dto.common.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

/**
 * @author christiansoriani on 31/01/24
 * @project TEFO_BE
 */
public final class PaginationUtility {

    private PaginationUtility() {
    }

    public static <T> PageDTO generatePagination(Page<T> page) {
        int pageNumber = page.getNumber() + 1;
        int pageSize = page.getSize();

        return PageDTO.builder()
                .size(pageSize)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(pageNumber)
                .sortedBy(page.getSort().toString())
                .build();
    }

    public static Pageable checkPagination(Integer page, Pageable pageable) {
        if (Objects.isNull(page) || page <= 0) {
            pageable = Pageable.unpaged();
        }
        return pageable;
    }

}

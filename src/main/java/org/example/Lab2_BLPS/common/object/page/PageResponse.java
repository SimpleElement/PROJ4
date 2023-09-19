package org.example.Lab2_BLPS.common.object.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> extends Page {
    private Integer totalPages;
    private Long totalElements;
    private List<T> content;
}

package org.example.Lab2_BLPS.common.object.page;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class PageToPageResponse<T> implements Converter<Page<T>, PageResponse<T>> {
    @Override
    public PageResponse<T> convert(Page source) {
        PageResponse<T> res = new PageResponse<>();

        res.setTotalPages(source.getTotalPages());
        res.setPageNumber(source.getNumber());
        res.setPageSize(source.getSize());
        return res;
    }
}

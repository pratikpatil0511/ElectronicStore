package com.bikkadit.electronicstore.helper;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

public class PageHelper {

    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> entityList = page.getContent();
        List<V> vDtoList = entityList.stream()
                .map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(vDtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }
}

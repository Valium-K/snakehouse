package dev.valium.snakehouse.module.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static dev.valium.snakehouse.infra.spring.SpringConfig.SLICE_SIZE;

public class PageRequestProvider {
    /**
     * @param orderBy "desc" or "asc"
     * @param page Integer
     * @param properties properties
     * @return
     */
    public static PageRequest get(String orderBy, Integer page, String... properties) {
        Sort.Direction direction = ("desc".equals(orderBy))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return PageRequest.of(page, SLICE_SIZE, direction, properties);
    }
}

package org.wlpiaoyi.dataSource.dao.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SuppressWarnings({"rawtypes", "unchecked"})
public class Page implements Serializable {

    private static final long serialVersionUID = 5593133328548180222L;
    private final List content = new ArrayList();
    private final long total;

    /**
     * Constructor of {@code PageResponse}.
     * 
     * @param content
     *            the content of this page, must not be {@literal null}.
     *            the paging information, can be {@literal null}.
     * @param total
     *            the total amount of items available
     */
    public Page(long total, List content) {

        if (null == content) {
            throw new IllegalArgumentException("Content must not be null!");
        }

        this.content.addAll(content);
        this.total = total;
    }

}

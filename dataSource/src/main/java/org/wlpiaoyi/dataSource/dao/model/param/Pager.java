package org.wlpiaoyi.dataSource.dao.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class Pager implements Serializable {

    private static final long serialVersionUID = -1639730758729788984L;

    /**
     * 请求的页码，从1开始
     */
    private int index;
    /**
     * 每页数据数
     */
    private int size;

    /**
     * 生成分页请求对象
     * 
     * @param index 　请求页码，１开始
     * @param size 　每页数据量，大于等于1
     */
    public Pager(int index, int size) {

        if (index < 1) {
            throw new IllegalArgumentException("Page index must not be less than one!");
        }

        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        this.index = index;
        this.size = size;
    }

    public int getPageSize() {
        return size;
    }

    public int getPageIndex() {
        return index;
    }

    public int getOffset() {
        return (index - 1) * size;
    }

    public boolean hasPrevious() {
        return index > 1;
    }

    public Pager previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    public Pager next() {
        return new Pager(getPageIndex() + 1, getPageSize());
    }

    public Pager previous() {
        return getPageIndex() == 1 ? this : new Pager(getPageIndex() - 1, getPageSize());
    }

    public Pager first() {
        return new Pager(1, getPageSize());
    }

}

package org.wlpiaoyi.dataSource.dao.model.param;

import lombok.Data;

/**
 * 通过id更新数据
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class UpdateByIdParam {

    /**
     * 被更新记录id
     */
    protected long id;

    /**
     * 更新到对象模型
     */
    protected Object target;

    public UpdateByIdParam(Integer id, Object target) {
        this.id = id;
        this.target = target;
    }

}
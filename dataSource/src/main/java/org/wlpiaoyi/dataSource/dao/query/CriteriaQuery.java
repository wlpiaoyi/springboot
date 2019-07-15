package org.wlpiaoyi.dataSource.dao.query;

import org.wlpiaoyi.dataSource.dao.model.param.Pager;
import org.wlpiaoyi.dataSource.dao.query.CriteriaQuery.Sort.Direction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态查询条件，其中criterias的每个item作为or条件，Criteria里面的criterions条件使用and条件。 先创建Criteria，按照其中方法与model中的field_name静态字段添加and条件。
 * 
 * @author wanglong(a)ikamobile.com
 *
 */
@Data
public class CriteriaQuery {

    /**
     * 是否排除重复
     */
    protected boolean distinct;
    /**
     * 查询条件集分组，组内部用or链接，集内部用and链接
     */
    protected List<Criteria> criterias;
    /**
     * 排序集，在count***方法中不生效
     */
    protected List<Sort> sorts;
    /**
     * 分页信息，只在selectPageByCriteriaQuery的时候有用
     */
    protected Pager pager;

    /**
     * 默认构造器
     */
    public CriteriaQuery() {}

    /**
     * 根据分页条件进行初始化
     * 
     * @param pager
     */
    public CriteriaQuery(Pager pager) {
        this.pager = pager;
    }

    /**
     * 增加排序条件：field direction
     * 
     * @param field
     * @param direction
     */
    public void addSort(final String field, final Direction direction) {
        if (sorts == null) {
            sorts = new ArrayList<Sort>();
        }
        sorts.add(new Sort(field, direction));
    }

    /**
     * 增加查询条件集
     * 
     * @param criteria
     */
    public void or(final Criteria criteria) {
        if (criterias == null) {
            criterias = new ArrayList<Criteria>();
        }
        criterias.add(criteria);
    }

    /**
     * 生成并增加查询条件集
     * 
     * @return
     */
    public Criteria or() {
        final Criteria criteria = new Criteria();
        if (criterias == null) {
            criterias = new ArrayList<Criteria>();
        }
        criterias.add(criteria);
        return criteria;
    }

    /**
     * 生成查询条件集，如果之前没有其他条件集，则直接放入条件集组合，如果已经有其他条件集，则单纯返回
     * 
     * @return
     */
    public Criteria createCriteria() {
        final Criteria criteria = new Criteria();
        if (criterias == null) {
            criterias = new ArrayList<Criteria>();
        }
        if (criterias.isEmpty()) {
            criterias.add(criteria);
        }
        return criteria;
    }

    /**
     * 清空条件，还原状态
     */
    public void clear() {
        criterias = null;
        distinct = false;
        pager = null;
        sorts = null;
    }

    /**
     * 排序条件
     * 
     * @author wanglong(a)ikamobile.com
     *
     */
//    @Data
    public static class Sort {
        /**
         * 排序字段名（使用model额静态属性）
         */
        private final String field;
        /**
         * 排序方式（使用Direction内部类）
         */
        private final Direction direction;

        /**
         * 根据条件构造field direction排序
         * 
         * @param field
         * @param direction
         */
        public Sort(final String field, final Direction direction) {
            this.field = field;
            this.direction = direction;
        }

        /**
         * 根据条件构造field direction排序
         * 
         * @param field
         * @param direction
         */
        public Sort(final String field, final String direction) {
            this.field = field;
            this.direction = Direction.valueOfCaseInsensitive(direction);
        }

        /**
         * 排序方式
         * 
         * @author wanglong(a)ikamobile.com
         *
         */
        public enum Direction {
            /**
             * 升序
             */
            ASC("asc"),
            /**
             * 降序
             */
            DESC("desc");

            private String direction;

            /**
             * 根据排序名字字符串初始化
             * 
             * @param direction
             */
            Direction(final String direction) {
                this.direction = direction;
            }

            /**
             * Value of case insensitive.
             * 
             * @param value
             * @return
             */
            public static Direction valueOfCaseInsensitive(final String value) {
                final String valueUpper = value.toUpperCase();
                return Direction.valueOf(valueUpper);
            }

            /**
             * Gets direction.
             * 
             * @return the direction
             */
            public String getDirection() {
                return this.direction;
            }
        }

    }

}
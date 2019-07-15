<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${entitypackage};

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * 
 * ${table.remarks} Database Model
 * Generated automaticly
 * @version 1.0
 * @since 1.0
 *
 */
@Data
@SuppressWarnings("serial")
public class ${entityHead}${className} implements Serializable {
    
    // alias
    public static final String TABLE = "${table.sqlName}";
    <#list table.columns as column>
    // column name of ${column.columnNameLower}
    public static final String FIELD_${column.constantName} = "${column.sqlName}";
    </#list>

    // columns START
    <#list table.columns as column>
    /**
     * ${column.remarks}
     * db_column: ${column.sqlName} 
     */    
    <#if !column.enumColumn>
    private ${column.simpleJavaType} ${column.columnNameLower};
    </#if>
    <#if column.enumColumn>
    private ${column.enumClassName} ${column.columnNameLower};
    </#if>
    <#list table.importedKeys.associatedTables?values as foreignKey>
    <#list foreignKey.parentColumns?values as fkColumn>
    <#if fkColumn == column.sqlName>
    /**
     * ${column.remarks}
     */ 
    private ${foreignKey.sqlTable.className} ${column.columnNameLower?replace("Id", "")};
    </#if>
    </#list>
    </#list>
    </#list>
    // columns END

    <#list table.columns as column>
    <#if column.enumColumn>
    /**
     * ${column.remarks}信息枚举值
     */ 
    @Getter
    public static enum ${column.enumClassName} {
        <#list column.enumList as item>
        ${item.enumAlias}("${item.enumDesc}")<#if item_has_next>,</#if><#if !item_has_next>;</#if>
        </#list>
        
        private final String desc;
        
        ${column.enumClassName}(String desc) {
            this.desc = desc;
        }
    }
    </#if>
    </#list>

}

package org.wlpiaoyi.dataSource.repository;


import org.springframework.data.jpa.repository.Query;
import org.wlpiaoyi.dataSource.entity.DouyinTaskL1;

import java.util.List;

public interface DouyinTaskL1Repository extends EntityJpaRepository<DouyinTaskL1, Long> {


    @Query(nativeQuery =true,value = "SELECT * FROM douyin_task_l1 tl1 where tl1.status = ?1 " +
            "limit 0, ?1")
    List<DouyinTaskL1> scanUncompleteTask(int size);


}

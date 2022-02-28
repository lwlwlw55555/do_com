package com.domdd.service.es;

import com.domdd.model.es.Applog;
import com.domdd.model.es.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author lw
 * @date 2022/2/16 4:27 下午
 */
public interface ApplogService extends ElasticsearchRepository<Applog, String> {
    List<Applog> findApplogBy(String by);

    List<Applog> findApplogByMessage(String message);

//    Applog findTopBy
}

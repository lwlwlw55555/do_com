package com.domdd.mapper.es;

import com.domdd.model.es.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author lw
 * @date 2022/2/15 5:20 下午
 */
public interface EsUserService extends ElasticsearchRepository<User, Integer> {
    //根据name查询
    List<User> findByName(String name);

    //根据name和info查询
    List<User> findByNameAndInfo(String name, String info);
}

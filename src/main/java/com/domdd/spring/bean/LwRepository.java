package com.domdd.spring.bean;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author lw
 * @date 2022/2/18 5:47 下午
 */
@NoRepositoryBean
public interface LwRepository<T, ID> extends PagingAndSortingRepository<T, ID> {

}

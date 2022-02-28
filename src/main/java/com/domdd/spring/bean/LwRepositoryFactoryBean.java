package com.domdd.spring.bean;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * @author lw
 * @date 2022/2/18 2:55 下午
 */
//@NoRepositoryBean
public class LwRepositoryFactoryBean <T extends Repository<S, ID>, S, ID extends Serializable>
        extends RepositoryFactoryBeanSupport<T, S, ID> {

    /**
     * Creates a new {@link RepositoryFactoryBeanSupport} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    protected LwRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    /**
     * @author lw
     * @date 2022-02-18 15:02
     * @param
     * @return org.springframework.data.repository.core.support.RepositoryFactorySupport
     * 自己写一个也是要实现这个方法，该方法是RepositoryFactoryBeanSupport的afterPropertiesSet()中调用的
     * {@link RepositoryFactoryBeanSupport#afterPropertiesSet()}
     * {@link org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean#createRepositoryFactory()}
     */
    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return null;
    }
}

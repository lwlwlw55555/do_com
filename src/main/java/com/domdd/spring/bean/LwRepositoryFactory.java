package com.domdd.spring.bean;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformationCreator;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author lw
 * @date 2022/2/18 4:50 下午
 */
@NoRepositoryBean
public class LwRepositoryFactory extends RepositoryFactorySupport {
    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return null;
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        return null;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return null;
    }

//    @Override
//    public <T, ID> ElasticsearchEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
//        return entityInformationCreator.getEntityInformation(domainClass);
//    }
//
//    @Override
//    protected Object getTargetRepository(RepositoryInformation metadata) {
//        return getTargetRepositoryViaReflection(metadata, getEntityInformation(metadata.getDomainType()),
//                elasticsearchOperations);
//    }
//
//    @Override
//    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//        if (isQueryDslRepository(metadata.getRepositoryInterface())) {
//            throw new IllegalArgumentException("QueryDsl Support has not been implemented yet.");
//        }
//
//        return SimpleElasticsearchRepository.class;
//    }

}
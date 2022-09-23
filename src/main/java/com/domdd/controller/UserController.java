package com.domdd.controller;

import com.domdd.controller.base.resp.BaseResp;
import com.domdd.model.es.User;
import com.domdd.service.es.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author lw
 * @date 2022/2/15 5:23 下午
 */
@Api(tags = "[user]")
@RestController
public class UserController {
    @Autowired(required = false)
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Resource
    private UserService esUserService;

    private final String[] names = {"诸葛亮", "曹操", "李白", "韩信", "赵云", "小乔", "狄仁杰", "李四", "诸小明", "王五"};
    private final String[] infos = {"我来自中国的一个小乡村，地处湖南省", "我来自中国的一个大城市，名叫上海，人们称作魔都"
            , "我来自东北，家住大囤里，一口大碴子话"};

    @GetMapping("saveUser")
    @ApiOperation("存数据")
    @ApiOperationSupport(author = "lw")
    public BaseResp saveUser() {
        //添加索引mapping    索引会自动创建但mapping自只用默认的这会导致分词器不生效 所以这里我们手动导入mapping
        elasticsearchTemplate.putMapping(User.class);
        Random random = new Random();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
//            user.setId(i);
            user.setName(names[random.nextInt(9)]);
            user.setAge(random.nextInt(40) + i);
            user.setInfo(infos[random.nextInt(2)]);
            users.add(user);
        }
        Iterable<User> users1 = esUserService.saveAll(users);
        return BaseResp.success(users1);
    }

    @GetMapping("getDataById")
    @ApiOperation("根据id查询数据")
    @ApiOperationSupport(author = "lw")
    public BaseResp getDataById(Integer id) {
        return BaseResp.success(esUserService.findById(id));
    }

    @GetMapping("getAllDataByPage")
    @ApiOperation("分页查询所有数据")
    @ApiOperationSupport(author = "lw")
    public BaseResp getAllDataByPage() {
        //本该传入page和size，这里为了方便就直接写死了
        Pageable page = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<User> all = esUserService.findAll(page);
        return BaseResp.success(all.getContent());
    }

    @GetMapping("getDataByName")
    @ApiOperation("根据名字查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp getDataByName(String name) {
        return BaseResp.success(esUserService.findByName(name));
    }

    @GetMapping("getDataByNameAndInfo")
    @ApiOperation("根据名字和介绍查询")
    @ApiOperationSupport(author = "lw")
    public BaseResp getDataByNameAndInfo(String name, String info) {
        //这里是查询两个字段取交集，即代表两个条件需要同时满足
        return BaseResp.success(esUserService.findByNameAndInfo(name, info));
    }

    @ApiOperation("查询高亮显示")
    @GetMapping("getHightByUser")
    @ApiOperationSupport(author = "lw")
    public BaseResp getHightByUser(String value) {
        //根据一个值查询多个字段  并高亮显示  这里的查询是取并集，即多个字段只需要有一个字段满足即可
        //需要查询的字段
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("info", value))
                .should(QueryBuilders.matchQuery("name", value));
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("info")
                        , new HighlightBuilder.Field("name"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>"))
                .build();
        //查询
        SearchHits<User> search = elasticsearchTemplate.search(searchQuery, User.class);
//        elasticsearchTemplate.queryForList();
        //得到查询返回的内容
        List<SearchHit<User>> searchHits = search.getSearchHits();
        //设置一个最后需要返回的实体类集合
        List<User> users = new ArrayList<>();
        //遍历返回的内容进行处理
        for (SearchHit<User> searchHit : searchHits) {
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            //将高亮的内容填充到content中
            searchHit.getContent().setName(highlightFields.get("name") == null ? searchHit.getContent().getName() : highlightFields.get("name").get(0));
            searchHit.getContent().setInfo(highlightFields.get("info") == null ? searchHit.getContent().getInfo() : highlightFields.get("info").get(0));
            //放到实体类中
            users.add(searchHit.getContent());
        }
        return BaseResp.success(users);
    }
}

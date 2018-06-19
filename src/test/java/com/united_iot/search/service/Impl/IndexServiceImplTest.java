package com.united_iot.search.service.Impl;


import com.united_iot.search.dataobject.IndexInfo;
import com.united_iot.search.dataobject.mapper.IndexMapper;
import com.united_iot.search.repository.IndexRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 16:23
 * @project_name： search
 * @Description ：
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
public class IndexServiceImplTest {

    @Autowired
    private IndexRepository indexRepository;


    @Autowired
    private IndexMapper indexMapper;

    @Test
    public void createIndexNew() throws Exception {

        //Index indexInfo = new Index();
        IndexInfo indexInfo = new IndexInfo();
        indexInfo.setName("test");
        indexInfo.setAppid("test1111");


       //indexMapper.insertByObject(indexInfo);
       // Index indexInfo1 = indexRepository.save(indexInfo);
        IndexInfo indexInfo1 = indexRepository.save(indexInfo);
        Assert.assertNotNull(indexInfo1);
    }

}
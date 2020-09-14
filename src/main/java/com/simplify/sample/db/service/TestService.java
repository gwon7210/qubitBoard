package com.simplify.sample.db.service;

import com.simplify.sample.db.dto.*;
import com.simplify.sample.db.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    TestMapper testMapper;

    public List<Test> getAll() throws Exception{
        return testMapper.getAll();
    }

    public void insertMain(memberVO map) throws Exception{
        testMapper.insertMainList(map);
    }

    public memberVO checkUserInfo(memberVO map) throws Exception{
        return testMapper.checkUserInfo(map);
    }

    public void insertContent(contentVO con) throws Exception{
        testMapper.insertContent(con);
    }

    public List<contentVO> getAllContent() throws Exception{
        return testMapper.getAllContent();
    }

    public List<allcontentVO> getContent() throws Exception{
        return testMapper.getContent();
    }

    public contentVO getContentDetail(contentVO con) throws Exception{
        return testMapper.getContentDetail(con);
    }

    public List<contentVO> findContentById(contentVO con) throws Exception{
        return testMapper.findContentById(con);
    }

    public void updateContent(contentVO con) throws Exception{
        testMapper.updateContent(con);
    }

    public void insertCommnet(commentVO con) throws Exception{
        testMapper.insertCommnet(con);
    }

    public List<commentVO> findCommentsByBoardId(contentVO con) throws Exception {
        return testMapper.findCommentsByBoardId(con);
    }

    public void deleteContentById(deletecontentVO con) throws Exception {
        testMapper.deleteContentById(con);
    }

    public List<contentVO> searchContentByWriter(String id) throws Exception {
        return testMapper.searchContentByWriter(id);
    }

}

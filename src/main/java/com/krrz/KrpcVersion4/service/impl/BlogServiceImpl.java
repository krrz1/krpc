package com.krrz.KrpcVersion4.service.impl;


import com.krrz.KrpcVersion4.domain.Blog;
import com.krrz.KrpcVersion4.service.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}

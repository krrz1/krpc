package com.krrz.KrpcVersion8.service.impl;


import com.krrz.KrpcVersion8.annotation.KrpcService;
import com.krrz.KrpcVersion8.domain.Blog;
import com.krrz.KrpcVersion8.service.BlogService;
@KrpcService(value = BlogService.class)
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}

package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.Article;
import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.request.PublishArticleRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticlesMapper articlesMapper;

    @Override
    public List<Article> list(Integer pageNumber) {
        int pageSize = 14;
        int offset = (pageNumber - 1) * pageSize;
        return articlesMapper.selectAll(offset, pageSize);
    }

    @Override
    public void publish(PublishArticleRequest publishArticleRequest) {
        // 清除文章标题的空白字符，包括空格、换行符、制表符等，并赋值给文章标题属性
        publishArticleRequest.setTitle(publishArticleRequest.getTitle().trim());
        // 清除文章内容的空白字符，包括空格、换行符、制表符等，并赋值给文章内容属性
        publishArticleRequest.setContent(publishArticleRequest.getContent().trim());

        Article article = new Article();
        // 将请求参数复制到文章对象中，忽略content属性
        BeanUtils.copyProperties(publishArticleRequest, article, "content");
        // 设置文章作者ID
        article.setUserId(CurrentHolder.getCurrentId());
        // 如果文章内容不为空则校验文章内容字符数量是否大于300，如果小于300则赋值给内容预览属性，如果大于300则截取前300个字符作为内容预览属性，将文章内容赋值给内容属性
        // 考虑到string.length()方法只能计算char数量，有的字符需要多个char表示，按照unicode码点计算字符数量
        String publishArticleRequestContent = publishArticleRequest.getContent();
        if (publishArticleRequestContent != null) {
            // 按照unicode码点计算文章内容字符数量
            int contentLength = publishArticleRequestContent.codePointCount(0, publishArticleRequestContent.length());
            if (contentLength > 300) {
                // 截取前300个字符作为内容预览属性
                article.setContentPreview(publishArticleRequestContent.substring(0, 300));
                // 将文章内容赋值给内容属性
                article.setContent(publishArticleRequestContent);
            } else {
                article.setContentPreview(publishArticleRequestContent);
                article.setContent(publishArticleRequestContent);
            }
        }
        // 调用 Mapper 插入数据
        articlesMapper.insert(article);
    }
}

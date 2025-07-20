package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.dto.ArticleListDTO;
import com.songheqing.microforum.entity.Article;
import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.request.ArticlePublishRequest;
import com.songheqing.microforum.service.ImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ArticlesServiceImpl implements ArticlesService {

    /**
     * 文章Mapper
     */
    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private ImageService imageService;

    /**
     * 查询文章列表
     * 
     * @param pageNumber 页码
     * @return 文章列表
     */
    @Override
    public List<ArticleListDTO> list(Integer pageNumber) {
        int pageSize = 14;
        int offset = (pageNumber - 1) * pageSize;
        return articlesMapper.selectAll(offset, pageSize);
    }

    /**
     * 上传文件的目录
     */
    @Value("${app.upload-dir}")
    private String UPLOAD_DIR;

    /**
     * 发布文章
     * 
     * @param publishArticleRequest 发布文章请求参数
     * @param images                图片列表
     * @throws IOException 文件操作失败
     */
    @Override
    public void publish(ArticlePublishRequest publishArticleRequest, List<MultipartFile> images) throws IOException {
        // 清除文章标题的空白字符，包括空格、换行符、制表符等，并赋值给文章标题属性
        publishArticleRequest.setTitle(publishArticleRequest.getTitle().trim());
        // 清除文章内容的空白字符，包括空格、换行符、制表符等，并赋值给文章内容属性
        publishArticleRequest.setContent(publishArticleRequest.getContent().trim());

        Article article = new Article();
        BeanUtils.copyProperties(publishArticleRequest, article, "content");
        Integer userId = CurrentHolder.getCurrentId();
        article.setUserId(userId);
        String publishArticleRequestContent = publishArticleRequest.getContent();
        if (publishArticleRequestContent != null) {
            int contentLength = publishArticleRequestContent.codePointCount(0, publishArticleRequestContent.length());
            if (contentLength > 300) {
                article.setContentPreview(publishArticleRequestContent.substring(0, 300));
                article.setContent(publishArticleRequestContent);
            } else {
                article.setContentPreview(publishArticleRequestContent);
                article.setContent(publishArticleRequestContent);
            }
        }

        // 插入文章，主键回填
        articlesMapper.insert(article);
        Integer articleId = article.getId();

        // 图片相关操作交给 ImageService
        article.setCoverType(1);
        imageService.saveImages(images, "ARTICLE", articleId);

    }
}

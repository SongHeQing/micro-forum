package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.ArticleEntity;
import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.vo.ArticleDetailVO;
import com.songheqing.microforum.vo.ArticleListVO;
import com.songheqing.microforum.request.ArticlePublishRequest;
import com.songheqing.microforum.service.FileStorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private FileStorageService imageService;

    /**
     * 查询文章列表
     * 
     * @param pageNumber 页码
     * @return 文章列表
     */
    @Transactional(readOnly = true)
    @Override
    public List<ArticleListVO> list(Integer pageNumber) {
        int pageSize = 14;
        int offset = (pageNumber - 1) * pageSize;
        Long userId = CurrentHolder.getCurrentId();
        log.info("userId:{}", userId);

        // 现在TypeHandler会自动处理mediaUrls的转换，无需手动处理
        List<ArticleListVO> articleList = articlesMapper.selectAll(offset, pageSize, userId);
        return articleList;
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

        ArticleEntity article = new ArticleEntity();
        BeanUtils.copyProperties(publishArticleRequest, article, "content");
        Long userId = CurrentHolder.getCurrentId();
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

        // 判断是否上传了图片
        if (images != null && !images.isEmpty()) {
            // 图片相关操作交给 ImageService
            article.setMediaType(1);
            List<String> mediaUrls = imageService.saveImages(images, "ARTICLE");
            // 转换mediaUrls为空格分割的字符串
            String mediaUrlsStr = String.join(" ", mediaUrls);
            article.setMediaUrls(mediaUrlsStr);
        }

        // 插入文章，主键回填
        articlesMapper.insert(article);
    }

    @Override
    public ArticleDetailVO getDetail(Long id) {
        Long userId = CurrentHolder.getCurrentId();
        return articlesMapper.selectDetailById(id, userId);
    }
}

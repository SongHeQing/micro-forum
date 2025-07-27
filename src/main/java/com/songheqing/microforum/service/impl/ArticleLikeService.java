
package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.ArticleLikeEntity;
import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.mapper.ArticleLikesMapper;
import com.songheqing.microforum.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikesMapper articleLikesMapper;
    @Autowired
    private ArticlesMapper articlesMapper; // 用于更新文章点赞量

    /**
     * 切换文章点赞状态：如果已点赞则取消，如果未点赞则点赞。
     * 整个操作在一个事务中，保证原子性。
     * 
     * @param articleId 文章ID
     * @return true表示点赞成功，false表示取消点赞成功
     */
    @Transactional // 保证点赞记录和文章点赞计数更新的原子性
    public boolean toggleLike(Long articleId) {
        // 从 CurrentHolder 获取当前用户ID
        Long userId = CurrentHolder.getCurrentId();

        // 1. 查询是否已点赞
        boolean exists = articleLikesMapper.existsByArticleIdAndUserId(articleId, userId);

        if (exists) {
            // 已点赞，执行取消点赞
            int deletedRows = articleLikesMapper.deleteByArticleIdAndUserId(articleId, userId);
            if (deletedRows > 0) {
                articlesMapper.decrementLikeCount(articleId); // 文章点赞量-1
                System.out.println("用户 " + userId + " 取消点赞文章 " + articleId);
                return false; // 返回false表示已取消点赞
            }
            return true; // 如果删除失败（理论上不应发生），保持原状态
        } else {
            // 未点赞，执行点赞
            ArticleLikeEntity articleLike = new ArticleLikeEntity();
            articleLike.setUserId(userId);
            articleLike.setArticleId(articleId);
            int insertedRows = articleLikesMapper.insert(articleLike);
            if (insertedRows > 0) {
                articlesMapper.incrementLikeCount(articleId); // 文章点赞量+1
                System.out.println("用户 " + userId + " 点赞文章 " + articleId);
                return true; // 返回true表示已点赞
            }
            return false; // 如果插入失败（理论上不应发生），保持原状态
        }
    }

    /**
     * 查询当前用户是否点赞了某篇文章。
     * 
     * @param articleId 文章ID
     * @return true表示已点赞，false表示未点赞
     */
    @Transactional(readOnly = true) // 只读事务
    public boolean isArticleLikedByUser(Long articleId) {
        Long userId = CurrentHolder.getCurrentId(); // 获取当前用户ID
        return articleLikesMapper.existsByArticleIdAndUserId(articleId, userId);
    }
}
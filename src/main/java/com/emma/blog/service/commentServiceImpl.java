package com.emma.blog.service;

import com.emma.blog.mapper.CommentMapper;
import com.emma.blog.entity.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Emma
 * @create 2020 - 06 - 02 - 11:21
 */
@Service
public class commentServiceImpl implements commentService {

    @Autowired
    private CommentMapper commentMapper;

    /*
     按照blogId查找，并且ParentComment为空
     */
    @Override
    public List<Comment> listCommentByBlogId(Integer blogId) {

        List<Comment> commentList = commentMapper.findByBlogIdAndParentCommentNull(blogId);
        for(Comment comment : commentList){
            //拿到子评论
            Integer ParentId = comment.getId();
            List<Comment> childComment = commentMapper.getChildCommentByParentId(ParentId);
            comment.setReplayComments(childComment);
        }
        return eachComment(commentList);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Integer parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            //获取ParentComment并保存
            comment.setParentComment(commentMapper.getCommentById(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        commentMapper.insertSelective(comment);
        return commentMapper.getCommentById(comment.getId());
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplayComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplayComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        Integer ParentId = comment.getId();
        List<Comment> childComment = commentMapper.getChildCommentByParentId(ParentId);
        comment.setReplayComments(childComment);

        if (comment.getReplayComments().size()>0) {
            List<Comment> replys = comment.getReplayComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                Integer replyId = reply.getId();
                List<Comment> replyComment = commentMapper.getChildCommentByParentId(replyId);
                reply.setReplayComments(replyComment);

                if (reply.getReplayComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}

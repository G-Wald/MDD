package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentsByArticleId(Integer articleId){
        return this.commentRepository.findByArticleId(articleId);
    }

    public Comment save(Comment comment){
        return this.commentRepository.save(comment);
    }
}

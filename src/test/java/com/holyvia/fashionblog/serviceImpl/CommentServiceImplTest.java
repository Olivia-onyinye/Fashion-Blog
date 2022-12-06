package com.holyvia.fashionblog.serviceImpl;

import com.holyvia.fashionblog.dtos.CommentDto;
import com.holyvia.fashionblog.enums.Gender;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.UnauthorizedOperationException;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.models.Comment;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.repositories.BlogPostRepo;
import com.holyvia.fashionblog.repositories.CommentRepo;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.serviceImpl.CommentServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentServiceImplTest {
    @Mock
    CommentRepo commentRepo;

    @Mock
    UserRepo userRepo;

    @Mock
    BlogPostRepo blogPostRepo;

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    HttpSession session;

    private BlogPost blogPost;
    private User user;
    private Comment comment;
    private CommentDto commentDto;
    private List<Comment> commentLists;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blogPost = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").comments(new ArrayList<>()).build();
        when(blogPostRepo.findById(3L)).thenReturn(Optional.ofNullable(blogPost));
        user = User.builder().user_id(1L).name("Victor Madu").email("madu@gmail.com").gender(Gender.MALE).password("unserious")
                .role(Role.ADMIN).blogPosts(new HashSet<>()).build();
        session.setAttribute("user_id", 1);
        when(session.getAttribute("user_id")).thenReturn(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
    comment = Comment.builder().commenterName("Author").email("author@gmail.com").body("interesting").build();
    commentDto = CommentDto.builder().commenterName("Author").email("author@gmail.com").body("interesting").build();
    commentLists = new ArrayList<>();
    }

    @Test
    void createComment() {
        when(commentRepo.save(comment)).thenReturn(comment);
        Comment actual = commentService.createComment(commentDto,3L);
        Assertions.assertEquals(comment.getCommenterName(), actual.getCommenterName());
        Assertions.assertEquals(comment.getEmail(), actual.getEmail());
        Assertions.assertEquals(comment.getBody(), actual.getBody());
    }

    @Test
    void viewCommentById() {
        comment = Comment.builder().comment_id(1L).commenterName("Author").email("author@gmail.com").body("interesting").build();
        when(commentRepo.findById(1L)).thenReturn(Optional.ofNullable(comment));
        Assertions.assertEquals(commentDto, commentService.viewCommentById(1L));
    }

    @Test
    void getAllCommentsOnAPost() {
        Comment comment1 = Comment.builder().commenterName("Author").email("author@gmail.com").body("interesting").build();
        commentLists.add(comment1);
        BlogPost blogPost2 = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(commentLists).build();
        when(blogPostRepo.findById(2L)).thenReturn(Optional.of(blogPost2));
        List<Comment> commentLi = commentService.getAllCommentsOnAPost(2L);
        Assertions.assertEquals(commentLi.size(), blogPost2.getComments().size());
    }

    @Test
    void countNumberOfComments() {
        Comment comment1 = Comment.builder().commenterName("Author").email("author@gmail.com").body("interesting").build();
        commentLists.add(comment1);
        BlogPost blogPost2 = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(commentLists).build();
        when(blogPostRepo.findById(2L)).thenReturn(Optional.of(blogPost2));
        commentService.countNumberOfComments(2L);
        Assertions.assertEquals(1, blogPost2.getComments().size());
    }

    @Test
    void deleteCommentById() {
        Comment comment1 = Comment.builder().comment_id(1L).commenterName("Author").email("author@gmail.com").body("interesting").build();
        commentLists.add(comment1);
        BlogPost blogPost2 = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(commentLists).build();
        when(commentRepo.findCommentByCommentIdAndPostId(2L,1L)).thenReturn(comment1);
        commentService.deleteCommentById(2L, 1L);
        verify(commentRepo).delete(comment1);
    }

    @Test
    void toDeleteACommentShouldThrowExceptionIfUserIsNotAnAdmin() {
        User user2 = User.builder().user_id(1L).name("Victor Madu").email("madu@gmail.com").gender(Gender.MALE).password("unserious")
                .role(Role.VISITOR).blogPosts(new HashSet<>()).build();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user2));
        Comment comment1 = Comment.builder().comment_id(1L).commenterName("Author").email("author@gmail.com").body("interesting").build();
        commentLists.add(comment1);
        BlogPost blogPost2 = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(commentLists).build();
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->commentService.deleteCommentById(2L,1L));
    }
}
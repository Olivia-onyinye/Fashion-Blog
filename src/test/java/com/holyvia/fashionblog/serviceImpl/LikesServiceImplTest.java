package com.holyvia.fashionblog.serviceImpl;

import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.models.Likes;
import com.holyvia.fashionblog.repositories.BlogPostRepo;
import com.holyvia.fashionblog.repositories.LikesRepo;
import com.holyvia.fashionblog.services.serviceImpl.LikesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LikesServiceImplTest {
    @Mock
    LikesRepo likesRepo;
    @Mock
    BlogPostRepo blogPostRepo;
    @InjectMocks
    LikesServiceImpl likesService;

    private Likes likes;
    private BlogPost blogPost;
    private List<Likes> likesList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blogPost = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(new ArrayList<>()).build();
        likes = Likes.builder().blogPost(blogPost).build();
        likesList = new ArrayList<>();
        when(likesRepo.save(likes)).thenReturn(likes);
        when(blogPostRepo.findById(2L)).thenReturn(Optional.of(blogPost));

    }

    @Test
    void likeAPost() {
        likesService.likeAPost(2L);
        assertEquals(likes.getBlogPost(), blogPost);
    }

    @Test
    void countNumberOfLikes() {
       Likes likes2 = Likes.builder().blogPost(blogPost).build();
        likesList.add(likes2);
        blogPost = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(new ArrayList<>()).likesList(likesList).build();
        when(blogPostRepo.findById(2L)).thenReturn(Optional.of(blogPost));
        likesService.countNumberOfLikes(2L);
        assertEquals(1, blogPost.getLikesList().size());
    }

    @Test
    void unLikeAPost() {
        Likes likes2 = Likes.builder().like_id(4L).blogPost(blogPost).build();
        likesList.add(likes2);
        blogPost = BlogPost.builder().post_id(2L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .comments(new ArrayList<>()).likesList(likesList).build();
        when(blogPostRepo.findById(2L)).thenReturn(Optional.of(blogPost));
        when(likesRepo.findById(4L)).thenReturn(Optional.of(likes));
        likesService.unLikeAPost(2L, 4L);
        verify(likesRepo).delete(likes);
    }
}
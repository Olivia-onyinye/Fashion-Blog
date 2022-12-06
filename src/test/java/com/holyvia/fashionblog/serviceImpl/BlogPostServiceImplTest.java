package com.holyvia.fashionblog.serviceImpl;

import com.holyvia.fashionblog.dtos.BPostResponseDto;
import com.holyvia.fashionblog.dtos.BlogPostCreateDto;
import com.holyvia.fashionblog.dtos.BlogPostUpdateDto;
import com.holyvia.fashionblog.enums.Gender;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.EmptyListException;
import com.holyvia.fashionblog.exceptions.UnauthorizedOperationException;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.models.Category;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.pagination_criteria.BlogPostPages;
import com.holyvia.fashionblog.repositories.BlogPostRepo;
import com.holyvia.fashionblog.repositories.CategoryRepo;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.serviceImpl.BlogPostServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ContextConfiguration(classes = {BlogPostServiceImpl.class, BlogPostPages.class})
class BlogPostServiceImplTest {
    @Mock
    BlogPostRepo blogPostRepo;

    @Mock
    UserRepo userRepo;
    @Mock
    CategoryRepo categoryRepo;

    @Mock
    HttpSession session;

    @InjectMocks
    BlogPostServiceImpl blogPostService;

    @InjectMocks
    private BlogPostPages blogPostPages;
    private BlogPost blogPost;
    private User user;
    private BlogPostCreateDto blogPostCreateDto;
    private BPostResponseDto bPostResponseDto;
    private Category category;
    private User user1;
    private BlogPostUpdateDto bpud;
    private List<BlogPost> blogPostList;
    private List<BPostResponseDto> bPostResponseDtoList;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = User.builder().user_id(1L).name("Victor Madu").email("madu@gmail.com").gender(Gender.MALE).password("unserious")
                .role(Role.ADMIN).blogPosts(new HashSet<>()).build();
        session.setAttribute("user_id", 1);
        when(session.getAttribute("user_id")).thenReturn(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user1));
        category = Category.builder().category_id(1L).categoryName("Casuals").blogPosts(new ArrayList<>()).build();
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));
        blogPost = BlogPost.builder().title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .postAuthor(user1).category(category).build();
        blogPostCreateDto = BlogPostCreateDto.builder().title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();
        blogPostList = new ArrayList<>();
        bPostResponseDtoList = new ArrayList<>();
        when(blogPostRepo.findAll()).thenReturn(List.of(blogPost));
        bpud = BlogPostUpdateDto.builder().title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();

    }

    @Test
    void createPost() {
        when(blogPostRepo.save(blogPost)).thenReturn(blogPost);
      BlogPost actual = blogPostService.createPost(blogPostCreateDto, 1L);
        Assertions.assertEquals(blogPost.getTitle(), actual.getTitle());
        Assertions.assertEquals(blogPost.getDescription(), actual.getDescription());
        Assertions.assertEquals(blogPost.getImageUrl(), actual.getImageUrl());
        Assertions.assertEquals(blogPost.getPostAuthor(), actual.getPostAuthor());
        Assertions.assertEquals(blogPost.getCategory(), actual.getCategory());
    }

    @Test
    void shouldThrowExceptionIfUserIsNotAnAdmin() {
       User user2 = User.builder().user_id(1L).name("Victor Madu").email("madu@gmail.com").gender(Gender.MALE).password("unserious")
                .role(Role.VISITOR).blogPosts(new HashSet<>()).build();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user2));
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->blogPostService.createPost(blogPostCreateDto, 1L));
    }

    @Test
    void getAllPosts() {
        PageImpl<BlogPost> pageImpl = new PageImpl<>(new ArrayList<>());
       when(blogPostRepo.findAll((Pageable) any())).thenReturn(pageImpl);
      Page<BlogPost> blp = blogPostService.getAllPosts(blogPostPages);
      assertEquals(pageImpl, blp);
    }
    @Test
    void getAllPostsShouldThrowExceptionIfEmptyList() {
        List<BlogPost> blogPostList = new ArrayList<>();
        when(blogPostRepo.findAll()).thenReturn(List.of());
        Assertions.assertThrows(EmptyListException.class, ()->blogPostService.getAllPosts(blogPostPages));
    }

    @Test
    void deletePost() {
        blogPost = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .postAuthor(user1).category(category).build();
        when(blogPostRepo.findById(3l)).thenReturn(Optional.ofNullable(blogPost));
        blogPostService.deletePost(3L);
        verify(blogPostRepo).delete(blogPost);
    }

    @Test
    void shouldThrowExceptionIfUserIsNotAnAdminToDeletePost() {
        User user2 = User.builder().user_id(1L).name("Victor Madu").email("madu@gmail.com").gender(Gender.MALE).password("unserious")
                .role(Role.VISITOR).blogPosts(new HashSet<>()).build();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user2));
        blogPost = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .postAuthor(user1).category(category).build();
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->blogPostService.deletePost(3L));
    }

    @Test
    void updateBlogPost() {
        blogPost = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .postAuthor(user1).category(category).build();
        when(blogPostRepo.findById(3l)).thenReturn(Optional.ofNullable(blogPost));
        when(blogPostRepo.save(blogPost)).thenReturn(blogPost);
       BlogPost actual = blogPostService.updateBlogPost(bpud,3L);
        Assertions.assertEquals(blogPost, actual);
    }

    @Test
    void toEditPostShouldThrowExceptionIfUserIsNotAnAdmin() {
        User user2 = User.builder().user_id(1L).name("Victor Madu").email("madu@gmail.com").gender(Gender.MALE).password("unserious")
                .role(Role.VISITOR).blogPosts(new HashSet<>()).build();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user2));
        blogPost = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .postAuthor(user1).category(category).build();
        BlogPostUpdateDto bpud = BlogPostUpdateDto.builder().title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->blogPostService.updateBlogPost(bpud, 3L));
    }

    @Test
    void viewBlogPost() {
        blogPost = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk")
                .postAuthor(user1).category(category).build();
        bPostResponseDto = BPostResponseDto.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();
        when(blogPostRepo.findById(3L)).thenReturn(Optional.ofNullable(blogPost));
     Assertions.assertEquals(bPostResponseDto, blogPostService.viewBlogPost(3L));
    }

    @Test
    void viewPostByCategory() {
        BlogPost blogPost2 = BlogPost.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();
        blogPostList.add(blogPost2);
        Category category = Category.builder().category_id(2L).categoryName("Casuals").blogPosts(blogPostList).build();
        when(categoryRepo.findById(2L)).thenReturn(Optional.of(category));
        bPostResponseDto = BPostResponseDto.builder().post_id(3L).title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();
        bPostResponseDtoList.add(bPostResponseDto);
        List<BPostResponseDto> bPostResponseDtoList1 = blogPostService.ViewPostByCategory(2L);
        Assertions.assertEquals(bPostResponseDtoList1.size(), 1);

    }
}
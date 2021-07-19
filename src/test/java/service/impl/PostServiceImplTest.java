package service.impl;

import model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import repository.PostRepository;
import repository.jdbc.JdbcPostRepositoryImpl;
import service.PostService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {


    private static final Long id = 1L;
    private static final String content = "content";
    private static final Long writerId = 2L;

    @Mock
    private PostRepository postRepository;

    @Mock
    private List<Post> postList;

    @Spy
    private PostService postService;


    @Before
    public void setUp() {
        postRepository = new JdbcPostRepositoryImpl();
    }


    @Test
    public void getByIdTest() {
        doReturn(postRepository.getById(id)).when(postService).getById(id);
        assertEquals(postRepository.getById(id), postService.getById(1L));
    }


    @Test
    public void createTest() {
        doReturn(postRepository.create(writerId, content)).when(postService).create(writerId, content);
        assertEquals(postRepository.create(writerId, content), postService.create(2L,content));
    }


    @Test
    public void updateTest() {
        doReturn(postRepository.update(id, writerId, content)).when(postService).update(id,   2L, content);
        assertEquals(postRepository.update(id, 2L, content), postService.update(1L, writerId, content ));
    }


    @Test
    public void getAllTest() {
        doReturn(postList).when(postService).getAll();
        assertEquals(postList, postService.getAll());
    }

}

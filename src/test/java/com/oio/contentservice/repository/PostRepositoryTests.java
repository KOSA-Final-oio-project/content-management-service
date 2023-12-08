package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.jpa.PostImage;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testRepoRegister(){


        PostEntity postEntity = PostEntity.builder()
                .title("title.....")
                .content("content....")
                .nickName("nickName")
                .category("Q&A")
                .build();


        postRepository.save(postEntity);

    }

    @Test
    public void testInsertWithImages() {
        PostEntity postEntity = PostEntity.builder()
                .title("image Test")
                .content("첨부파일 테스트")
                .nickName("nickName")
                .category("Q&A")
                .build();

        for(int i = 0 ; i<3; i++){
            postEntity.addImage(UUID.randomUUID().toString(),"file" + i + ".jpg");
        }

        postRepository.save(postEntity);

    }

    @Test
    public void testReadWithImage() {
        Optional<PostEntity> result = postRepository.findByIdWithImages(2L);

        PostEntity post = result.orElseThrow();

        log.info(post);
        log.info("--------------");
        for(PostImage postImage : post.getImageSet()){
            log.info(postImage);
        }

    }

    @Test
    public void testModifyImages(){

        Optional<PostEntity> result = postRepository.findByIdWithImages(1L);
        PostEntity post = result.orElseThrow();

        post.clearImages();

        for(int i = 0 ; i<2; i++){
            post.addImage(UUID.randomUUID().toString(),"file" + i + ".jpg");
        }

        postRepository.save(post);

    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){
        replyRepository.deleteByPost_Pno(3L);
        postRepository.deleteById(3L);

    }

    @Test
    public void testInsertAll(){

        for(int i = 1; i<= 100; i++){

            PostEntity postEntity = PostEntity.builder()
                    .title("title.." + i)
                    .content("content.." + i)
                    .nickName("nickName.. " + i)
                    .category("Q&A")
                    .build();

            for (int j = 0; j<3; j++){
                if(i % 5 == 0) {
                    continue;
                }
                postEntity.addImage(UUID.randomUUID().toString(), i + "file" + j + ".jpg");
            }
            postRepository.save(postEntity);
        }
    }



}

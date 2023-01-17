package kr.hh.liverary.domain;

import kr.hh.liverary.domain.liveBoard.LiveBoard;
import kr.hh.liverary.domain.liveBoard.LiveBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class LiveBoardRepositoryTest {

    @Autowired
    LiveBoardRepository liveBoardRepository;

    @Test
    public void InsertTest(){
        IntStream.rangeClosed(1, 10).forEach(i -> {
            LiveBoard liveBoard = LiveBoard.builder()
                    .userId("SampleUser"+i)
                    .boardTitle("SampleTitle"+i)
                    .boardContent("Sample..." + i)
                    .build();

            //create!!
            liveBoardRepository.save(liveBoard);
        });
    }
    @Test
    public void SelectTest(){

        Long id = 10L;

        Optional<LiveBoard> result = liveBoardRepository.findById(id);

        System.out.println("=================================================");

        if(result.isPresent()){
            LiveBoard toDo = result.get();
            System.out.println(toDo);
        }
    }

    @Test
    public void UpdateTest(){
        LiveBoard liveBoard = LiveBoard.builder()
                            .id(10L)
                            .userId("SampleUser10")
                            .boardTitle("SampleTitle10")
                            .boardContent("UpdateTest...")
                            .build();

        liveBoardRepository.save(liveBoard);
    }
    @Test
    public void DeleteTest(){

        Long id = 10L;

        liveBoardRepository.deleteById(id);
    }

}


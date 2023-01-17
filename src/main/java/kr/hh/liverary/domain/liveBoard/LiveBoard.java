package kr.hh.liverary.domain.liveBoard;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_LiveBoard")
@Entity
public class LiveBoard {

    //- 게시글 테이블 ( id , 사용자 id , 제목 , 글 내용 , 등록일 , 수정일 )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200 , nullable = false)
    private String userId;

    @Column(length = 200 , nullable = false)
    private String boardTitle;

    @Column(columnDefinition = "text" , nullable = false)
    private String boardContent;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;


}

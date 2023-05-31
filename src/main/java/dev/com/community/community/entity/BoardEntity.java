package dev.com.community.community.entity;

import dev.com.community.common.BaseEntity;
import dev.com.community.user.entity.UserEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근제어자가 protected인 기본 생성자를 생성
@Data
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="title", nullable = false) // title이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public BoardEntity(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

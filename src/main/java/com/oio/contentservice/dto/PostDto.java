package com.oio.contentservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private Long pno;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String category;

    @NotEmpty
    private int status;

    @NotEmpty
    private int key;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}

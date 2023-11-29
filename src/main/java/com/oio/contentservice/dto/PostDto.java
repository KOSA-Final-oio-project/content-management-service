package com.oio.contentservice.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private Long pno;
    @NotEmpty
    private String memberEmail;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String category;
    @NotEmpty
    private String status;
    private String password;
}

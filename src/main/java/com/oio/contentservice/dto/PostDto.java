package com.oio.contentservice.dto;

import com.oio.contentservice.jpa.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NotNull
public class PostDto {

    private Long id;
    private String memberEmail;
    private String title;
    private String content;
    private Status status;


}

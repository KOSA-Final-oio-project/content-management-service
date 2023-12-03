package com.oio.contentservice.vo;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestReplyModify {

    private Long rno;
    @NotEmpty
    private String replyText;
}

package com.oio.contentservice.vo;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponsePostModify {

    private String title;
    private String content;
}

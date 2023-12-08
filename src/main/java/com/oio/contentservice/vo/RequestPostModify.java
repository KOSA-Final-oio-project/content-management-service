package com.oio.contentservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostModify {

    private Long pno;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    private List<String> fileNames;
}

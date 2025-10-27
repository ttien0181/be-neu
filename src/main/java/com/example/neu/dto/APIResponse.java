package com.example.neu.dto;

import com.example.neu.dto.common.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // thêm .builder() giúp gọi các pthuc cho từng thuộc tính
@JsonInclude(JsonInclude.Include.NON_NULL) // chỉ cho vào json các trường NON_NULL
public class APIResponse<T> {
    private String status; // ten bien tuong duong voi ten key trong json
    private List<ErrorDetail> errors;
    private T result;
}

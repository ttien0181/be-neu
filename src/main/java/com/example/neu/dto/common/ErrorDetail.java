package com.example.neu.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // @Getter, @Setter, @ToString, @Equals ,@HashCode.
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetail {
    private String field;
    private String errorMessage;
    private LocalDateTime timestamp;
}

package com.healthybites.dtos.advice;


import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdviceResponseDto {

	private String title;
	private String description;
	private LocalDateTime creationDate;
}

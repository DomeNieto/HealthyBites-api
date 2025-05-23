package com.healthybites.dtos.advice;


import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdviceResponseDto is a Data Transfer Object (DTO) that represents the response data for an advice.
 * It contains fields for the ID, title, description, and creation date of the advice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdviceResponseDto {
	
	private long id;
	private String title;
	private String description;
	private LocalDateTime creationDate;
}

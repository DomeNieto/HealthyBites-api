package com.healthybites.dtos.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityResponseDto {
	
	private Long id;
	private String name;
	private LocalDateTime registrationDate;
	
}

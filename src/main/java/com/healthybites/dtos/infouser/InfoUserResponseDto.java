package com.healthybites.dtos.infouser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoUserResponseDto {

	private Long id;
	private Double height;
	private Double weight;
	private String activityLevel;
	private String sex;
	private int age;
}

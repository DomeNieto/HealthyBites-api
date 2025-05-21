package com.healthybites.service.advice;

import java.util.List;

import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;

public interface AdviceService {
	
	List<AdviceResponseDto> getAllAdvices();
	AdviceResponseDto createAdvice(AdviceRequestDto adviceRequestDto);
	AdviceResponseDto updateAdvice(Long adviceId, AdviceRequestDto adviceRequest);
	void deleteAdvice(Long adviceId);
	
}

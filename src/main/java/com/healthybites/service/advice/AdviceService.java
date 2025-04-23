package com.healthybites.service.advice;

import java.util.List;

import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;

public interface AdviceService {
	
	public List<AdviceResponseDto> getAllAdvices();
	public AdviceResponseDto getAdviceById(Long adviceId);
	public AdviceResponseDto createAdvice(AdviceRequestDto adviceRequestDto);
	public AdviceResponseDto updateAdvice(Long adviceId, AdviceRequestDto adviceRequest);
	public void deleteAdvice(Long adviceId);
	
}

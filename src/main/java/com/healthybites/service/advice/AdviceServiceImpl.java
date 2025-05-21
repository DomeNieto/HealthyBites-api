package com.healthybites.service.advice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.entity.AdviceEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.mappers.advice.AdviceMapper;
import com.healthybites.repositoy.AdviceRepository;

@Service
public class AdviceServiceImpl implements AdviceService {

	private final static String ADVICE_NOT_FOUND = "Advice with id %d not found";
	@Autowired
	AdviceRepository adviceRepository;
	@Autowired
	AdviceMapper adviceMapper;

	@Override
	public List<AdviceResponseDto> getAllAdvices() {
		return adviceRepository.findAll().stream().map(adviceMapper::toAdviceResponseDto).toList();
	}

	@Override
	public AdviceResponseDto createAdvice(AdviceRequestDto adviceRequestDto) {
		AdviceEntity advice = adviceMapper.toAdvice(adviceRequestDto);
		advice.setCreationDate(LocalDateTime.now());
		return adviceMapper.toAdviceResponseDto(adviceRepository.save(advice));
	}

	@Override
	public AdviceResponseDto updateAdvice(Long adviceId, AdviceRequestDto adviceRequest) {
		AdviceEntity advice = validateAndGetAdvice(adviceId);
		advice.setTitle(adviceRequest.getTitle());
		advice.setDescription(adviceRequest.getDescription());
		advice.setCreationDate(LocalDateTime.now());
		return adviceMapper.toAdviceResponseDto(adviceRepository.save(advice));
	}

	@Override
	public void deleteAdvice(Long adviceId) {
		AdviceEntity advice = validateAndGetAdvice(adviceId);
		adviceRepository.delete(advice);
	}

	// helpers

	private AdviceEntity validateAndGetAdvice(Long adviceId) {
		return adviceRepository.findById(adviceId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ADVICE_NOT_FOUND, adviceId)));
	}

}

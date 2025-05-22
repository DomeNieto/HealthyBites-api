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

	/**
	 * This method retrieves all advices from the database and maps them to a list of
	 * AdviceResponseDto objects.
	 * 
	 * @return List<AdviceResponseDto> - A list of all advices in the system.
	 */
	@Override
	public List<AdviceResponseDto> getAllAdvices() {
		return adviceRepository.findAll().stream().map(adviceMapper::toAdviceResponseDto).toList();
	}

	/**
	 * This method creates a new advice in the database and returns the created advice as a
	 * response DTO.
	 * 
	 * @param adviceRequestDto - The request DTO containing the details of the advice to be
	 *                          created.
	 * @return AdviceResponseDto - The created advice as a response DTO.
	 */
	@Override
	public AdviceResponseDto createAdvice(AdviceRequestDto adviceRequestDto) {
		AdviceEntity advice = adviceMapper.toAdvice(adviceRequestDto);
		advice.setCreationDate(LocalDateTime.now());
		return adviceMapper.toAdviceResponseDto(adviceRepository.save(advice));
	}

	/**
	 * This method updates an existing advice in the database and returns the updated advice as a
	 * response DTO.
	 * 
	 * @param adviceId        - The ID of the advice to be updated.
	 * @param adviceRequest   - The request DTO containing the updated details of the advice.
	 * @return AdviceResponseDto - The updated advice as a response DTO.
	 */
	@Override
	public AdviceResponseDto updateAdvice(Long adviceId, AdviceRequestDto adviceRequest) {
		AdviceEntity advice = validateAndGetAdvice(adviceId);
		advice.setTitle(adviceRequest.getTitle());
		advice.setDescription(adviceRequest.getDescription());
		advice.setCreationDate(LocalDateTime.now());
		return adviceMapper.toAdviceResponseDto(adviceRepository.save(advice));
	}

	/**
	 * This method deletes an existing advice from the database.
	 * 
	 * @param adviceId - The ID of the advice to be deleted.
	 */
	@Override
	public void deleteAdvice(Long adviceId) {
		AdviceEntity advice = validateAndGetAdvice(adviceId);
		adviceRepository.delete(advice);
	}

	// helpers

	/**
	 * This method validates the existence of an advice by its ID and returns the advice entity.
	 * 
	 * @param adviceId - The ID of the advice to be validated.
	 * @return AdviceEntity - The advice entity if found.
	 * @throws ResourceNotFoundException - If the advice with the given ID is not found.
	 */
	private AdviceEntity validateAndGetAdvice(Long adviceId) {
		return adviceRepository.findById(adviceId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ADVICE_NOT_FOUND, adviceId)));
	}

}

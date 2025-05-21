package com.healthybites.controller.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthybites.api.ApiResponseDto;
import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.service.advice.AdviceServiceImpl;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AdviceController {

    private static final String ADVICE_RESOURCE = "/advices";
    private static final String ADVICE_ID_PATH = ADVICE_RESOURCE + "/{adviceId}";

    @Autowired
    private AdviceServiceImpl adviceService;

    @GetMapping(value = ADVICE_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<AdviceResponseDto>>> getAllAdvices() {
        List<AdviceResponseDto> advices = adviceService.getAllAdvices();
        ApiResponseDto<List<AdviceResponseDto>> response =
                new ApiResponseDto<>("Advices fetched successfully", HttpStatus.OK.value(), advices);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = ADVICE_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<AdviceResponseDto>> createAdvice(
            @Valid @RequestBody AdviceRequestDto adviceRequestDto) {
        AdviceResponseDto createdAdvice = adviceService.createAdvice(adviceRequestDto);
        ApiResponseDto<AdviceResponseDto> response =
                new ApiResponseDto<>("Advice created successfully", HttpStatus.CREATED.value(), createdAdvice);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = ADVICE_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<AdviceResponseDto>> updateAdvice(
            @PathVariable Long adviceId, @Valid @RequestBody AdviceRequestDto adviceRequestDto) {
        AdviceResponseDto updatedAdvice = adviceService.updateAdvice(adviceId, adviceRequestDto);
        ApiResponseDto<AdviceResponseDto> response =
                new ApiResponseDto<>("Advice updated successfully", HttpStatus.OK.value(), updatedAdvice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = ADVICE_ID_PATH)
    public ResponseEntity<Void> deleteAdvice(@PathVariable Long adviceId) {
        adviceService.deleteAdvice(adviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

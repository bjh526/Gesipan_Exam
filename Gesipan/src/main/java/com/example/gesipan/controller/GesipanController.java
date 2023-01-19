package com.example.gesipan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gesipan.model.dto.GesipanDTO;
import com.example.gesipan.model.dto.ResponseDTO;
import com.example.gesipan.model.entity.GesipanEntity;
import com.example.gesipan.service.GesipanService;

@RestController
@RequestMapping("/gesipan")
public class GesipanController {
	
	@Autowired
	private GesipanService service;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody GesipanDTO dto) {
		try {
			String userId = "temporary-user";
			
			// 엔티티로 변환
			GesipanEntity entity = GesipanDTO.toEntity(dto);
			
			// id null로 초기화
			entity.setId(null);
			
			// 임시 사용자 아이디 설정
			entity.setUserId(userId);
			
			// 서비스로 엔티티 생성
			List<GesipanEntity> entities = service.create(entity);
			
			// 스트림 이용해서 엔티티리스트를 dto리스트로 변환
			List<GesipanDTO> dtos = entities.stream().map(GesipanDTO::new).collect(Collectors.toList());
			
			// dto리스트 이용해서 response DTO 초기화
			ResponseDTO<GesipanDTO> response = ResponseDTO.<GesipanDTO>builder().data(dtos).build();
			
			// response 반환
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<GesipanDTO> response = ResponseDTO.<GesipanDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> list() {
		String userId = "temporary-user";
		
		// 서비스로 entity 리스트 가져오기
		List<GesipanEntity> entities = service.list(userId);
		
		// 스트림으로 dto리스트 변환
		List<GesipanDTO> dtos = entities.stream().map(GesipanDTO::new).collect(Collectors.toList());
		
		// 레스폰스 초기화
		ResponseDTO<GesipanDTO> response = ResponseDTO.<GesipanDTO>builder().data(dtos).build();
		
		// 레스폰스 리턴
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody GesipanDTO dto) {
		String userId = "temporary-user";
		
		GesipanEntity entity = GesipanDTO.toEntity(dto);
		
		entity.setUserId(userId);
		
		List<GesipanEntity> entities = service.update(entity);
		
		List<GesipanDTO> dtos = entities.stream().map(GesipanDTO::new).collect(Collectors.toList());
		
		ResponseDTO<GesipanDTO> response = ResponseDTO.<GesipanDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody GesipanDTO dto) {
		try {
			String userId = "temporary-user";
			
			GesipanEntity entity = GesipanDTO.toEntity(dto);
			
			entity.setUserId(userId);
			
			List<GesipanEntity> entities = service.delete(entity);
			
			List<GesipanDTO> dtos = entities.stream().map(GesipanDTO::new).collect(Collectors.toList());
			
			ResponseDTO<GesipanDTO> response = ResponseDTO.<GesipanDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<GesipanDTO> response = ResponseDTO.<GesipanDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
}

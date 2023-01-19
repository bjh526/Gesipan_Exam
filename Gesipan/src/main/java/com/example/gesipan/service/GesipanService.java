package com.example.gesipan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gesipan.model.entity.GesipanEntity;
import com.example.gesipan.repository.GesipanRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GesipanService {

	@Autowired
	private GesipanRepository repo;
	
	public List<GesipanEntity> create(final GesipanEntity entity) {
		// 유효성 검증
		validate(entity);
		
		repo.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getId());
		
		return repo.findByUserId(entity.getUserId());
	}
	
	public List<GesipanEntity> list(final String userId) {
		return repo.findByUserId(userId);
	}
	
	public List<GesipanEntity> update(final GesipanEntity entity) {
		validate(entity);
		
		final Optional<GesipanEntity> original = repo.findById(entity.getId());
		
		original.ifPresent(gesipan -> {
			gesipan.setTitle(entity.getTitle());
			gesipan.setDone(entity.isDone());
			
			repo.save(gesipan);
		});
		
		return list(entity.getUserId());
		
	}
	
	public List<GesipanEntity> delete(final GesipanEntity entity) {
		validate(entity);
		
		try {
			// 삭제
			repo.delete(entity);
		} catch (Exception e) {
			// 예외 발생시 id와 exception로깅
			log.error("error deleting entity", entity.getId(), e);
			
			// 컨트롤러로 예외 보내기 내부 로직 캡슐화를위해 e가아닌 오브젝트 리턴
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		
		// 새 리스트 리턴
		return list(entity.getUserId());
	}
	
	private void validate(final GesipanEntity entity) {
		if (entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		
		if (entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}
}

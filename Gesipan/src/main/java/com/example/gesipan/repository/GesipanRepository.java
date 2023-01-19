package com.example.gesipan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gesipan.model.entity.GesipanEntity;

@Repository
public interface GesipanRepository extends JpaRepository<GesipanEntity, String>{
	List<GesipanEntity> findByUserId(String userId);
}

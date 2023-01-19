package com.example.gesipan.model.dto;

import com.example.gesipan.model.entity.GesipanEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GesipanDTO {
	private String id;
	private String title;
	private boolean done;

	public GesipanDTO(final GesipanEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	public static GesipanEntity toEntity(final GesipanDTO dto) {
		return GesipanEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
}

package com.shinhan.soloplay.point;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDTO {
	Long pointId;
	String pointName;
	int amount;
	Timestamp createDate;
	int isAdd;
	int category;
}

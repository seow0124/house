package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.HouseInfo;
import com.ssafy.happyhouse.dto.InterestRegion;
public interface InterestRegionService {

	/**
	 * 아이디를 통해 특정 유저의 관심지역 조회
	 * @param id	유저의 id
	 * @return		조회된 관심지역들의 정보
	 */
	public List<InterestRegion> searchInterestRegion(String userid);
	
	/**
	 * 새로운 관심지역 추가
	 * @param ir	추가할 관심지역 정보
	 */
	public boolean addInterestRegion(InterestRegion ir);
	
	/**
	 * 관심지역 정보 삭제
	 * @param no	삭제할 관심지역의 일련번호
	 */
	public boolean deleteInterestRegion(InterestRegion ir);

	public List<HouseInfo> searchApt(String id);

	public boolean addApt(Map<String, Object> map);

	public int getNo(int i);
}

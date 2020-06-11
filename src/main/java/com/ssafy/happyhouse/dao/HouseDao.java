package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HousePageBean;

@Mapper
@Repository
public interface HouseDao {
		
	/**
	 * 검색 조건(key) 검색 단어(word)에 해당하는 아파트 거래 정보(HouseInfo)를  검색해서 반환.  
	 * @param bean  검색 조건과 검색 단어가 있는 객체
	 * @return 조회한 주택거래 목록
	 */
	public List<HouseDeal> searchAll(HousePageBean  bean);
	
	/**
	 * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환. 
	 * @param no	검색할 아파트 식별 번호
	 * @return		아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	public HouseDeal search(int no);

	/**
	 * 이 검색 조건으로 검색했을 때 조회되는 데이터의 수 반환
	 * @param bean 검색 조건이 있는 객체
	 * @return 이 조건으로 조회할 수 있는 데이터의 수
	 */
	public int numberOfData(HousePageBean bean);
}

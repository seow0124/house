package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.HouseDao;
import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HousePageBean;

@Service
public class HouseServiceImpl implements HouseService{
	
	private HouseDao dao;
	
	@Autowired
	public void setDao(HouseDao dao) {
		this.dao = dao;
	}

	@Override
	public List<HouseDeal> searchAll(HousePageBean bean) {		
		return dao.searchAll(bean);
	}

	@Override
	public HouseDeal search(int no) {
		return dao.search(no);
	}

	@Override
	public int numberOfData(HousePageBean bean) {
		return dao.numberOfData(bean);
	}

	@Override
	public List<HouseDeal> chartData(Map<String, Object> param) {
		return dao.chartData(param);
	}

	@Override
	public List<String> address() {
		return dao.address();
	}

	@Override
	public boolean addrToLng(Map<String, Object> param) {
		return dao.addrToLng(param) > 0;
	}

	@Override
	public List<String> gugunName(String sidoName) {
		return dao.gugunName(sidoName);
	}

	@Override
	public List<String> dongName(String gugunName) {
		return dao.dongName(gugunName);
	}

	@Override
	public List<String> sidoName() {
		return dao.sidoName();
	}

}





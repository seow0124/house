package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.Notice;

@Mapper
@Repository
public interface NoticeDao {
	int insertNotice(Notice notice);

	int updateNotice(Notice notice);

	int updateHitCount(int no) ;

	int deleteNotice(int no);

	List<Notice> getNoticeList();

	Notice getNotice(int no);
}

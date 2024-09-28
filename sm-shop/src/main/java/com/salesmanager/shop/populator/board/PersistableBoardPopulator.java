package com.salesmanager.shop.populator.board;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.board.PersistableBoard;

@Component
public class PersistableBoardPopulator extends AbstractDataPopulator<PersistableBoard, Board> {
	
	
	public Board populate(PersistableBoard source, Board target)
			throws ConversionException {

		try {
			// 현재 날짜와 시간 가져오기
	        Date currentDate = new Date();
	        // 원하는 형식으로 포맷터 생성
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	        // 날짜를 문자열로 변환
	        String formattedDate = formatter.format(currentDate);
			Validate.notNull(target, "Board target cannot be null");
			target.setId(source.getId());
			target.setTitle(source.getTitle());
			target.setWriter(source.getWriter());
			target.setEmail(source.getEmail());
			target.setBbsId(source.getBbsId());
			target.setOpen(source.getOpen());
			target.setNotice(source.getNotice());
			target.setType(source.getType());
			target.setState(source.getState());
			target.setContent(source.getContent());
		
			target.setReplyContent(source.getReplyContent());
			target.setSdate(source.getSdate().equals("") ? formattedDate : source.getSdate()+":00");
			target.setEdate(source.getEdate().equals("") ? "9999-12-31 23:59:59" : source.getEdate()+":59");
			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
			target.setViewCnt(source.getViewCnt());
		
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public Board populate(PersistableBoard source, Board target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Board createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}

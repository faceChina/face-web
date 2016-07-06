package com.zjlp.face.web.server.operation.member.domain;

import java.util.Comparator;





import com.zjlp.face.web.server.operation.member.domain.vo.ConsumeVo;

public class DateComparator implements Comparator<ConsumeVo> {


	@Override
	public int compare(ConsumeVo o1, ConsumeVo o2) {
		int flag = o1.getDate().compareTo(o2.getDate());
		return -flag;
	}

}

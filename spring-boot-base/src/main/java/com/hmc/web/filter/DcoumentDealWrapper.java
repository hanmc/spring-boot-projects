package com.hmc.web.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求包装类<p>
 *
 * @author hanmc
 * @date 2016-4-3
 */
public class DcoumentDealWrapper extends BaseServletRequestWrapper{

	
	private List<String> list = new ArrayList<String>();
    
    public DcoumentDealWrapper (HttpServletRequest request) {

        super(request);
        
        someInit();
    }

	private void someInit() {
		list.add("/tradeQuery.do");
		list.add("/tradeRefund.do");
	}

	@Override
	protected String documentDeal(HttpServletRequest request) {
		//在这里对流中的数据进行操作
		return getDocument(request);
	}
	


}

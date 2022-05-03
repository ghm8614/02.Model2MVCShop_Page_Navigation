package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	// 상품을 구매완료 상태로 바꾸는 컨트롤러
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 물건도착 버튼 클릭시, 넘어오는 데이터
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		String tranCode = request.getParameter("tranCode"); // 고정값 : 3
		System.out.println("tranNo: " + tranNo);
		System.out.println("tranCode: " + tranCode);

		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode(tranCode);
		System.out.println("컨트롤러에서의 " + purchase);

		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);

		// 현재페이지에 머물게 구현하기..
		return "forward:/listPurchase.do";
	}

}

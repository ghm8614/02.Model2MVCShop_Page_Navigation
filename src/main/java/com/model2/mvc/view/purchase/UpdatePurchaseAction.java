package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		// String buyerId = request.getParameter("buyerId");
		// hidden data.. session getAttri로 userId=buyerId 사용하면 되는데, 파라미터로 받아올 필요가 있나?

		System.out.println("tranNo : " + tranNo);
		System.out.println(request.getParameter("paymentOption"));
		System.out.println(request.getParameter("receiverName"));
		System.out.println(request.getParameter("receiverPhone"));
		System.out.println(request.getParameter("receiverAddr"));
		System.out.println(request.getParameter("receiverRequest"));
		System.out.println(request.getParameter("divyDate"));

		Purchase purchase = new Purchase();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		purchase.setBuyer(user);

		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));

		System.out.println("컨트롤러에서의 " + purchase);

		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updatePurchase(purchase);

		return "forward:/getPurchase.do?tranNo=" + tranNo;
	}

}

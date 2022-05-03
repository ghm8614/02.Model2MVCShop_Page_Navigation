package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// addPurchase.jsp hidden data : 1.prodNo 2.userId
		String prodNo = request.getParameter("prodNo");
		String buyerId = request.getParameter("buyerId");

		System.out.println("prodNo : " + prodNo);
		System.out.println("buyerId :" + buyerId);

		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProduct(Integer.parseInt(prodNo));
		System.out.println("컨트롤러에서의 " + product);

//		UserService userService = new UserServiceImpl();
//		User user = userService.getUser(buyerId);

		// 세션 사용
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Purchase purchase = new Purchase();

		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);

		// Transaction Table에 insert
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setTranCode("1"); // 구매완료 코드 : 1
		purchase.setDivyDate(request.getParameter("receiverDate"));

		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);

		// updatePurchase.jsp 로 data 전달
		request.setAttribute("purchase", purchase); // prodNo, buyerId 포함

		// 판매 중인 상품이 판매완료로 바뀌므로, updatePurchase.jsp
		return "forward:/purchase/addPurchaseView.jsp";
	}

}

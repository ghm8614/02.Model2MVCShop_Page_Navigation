package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {

	// 구매할 상품 정보 가져오기 (getProduct(prod_no))
	// 상품상세조회 -> 구매버튼 -> AddPurchaseView.do?prod_no=10021
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		
		// prod_no 로 getProduct(prod_no) 화면에 출력위해 os에 저장
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProduct(prodNo);
		
		request.setAttribute("product", product);
		
		// 동적인 페이지를 제공하므로, addPurchaseView.jsp X
		return "forward:/purchase/addPurchase.jsp";
	}

}

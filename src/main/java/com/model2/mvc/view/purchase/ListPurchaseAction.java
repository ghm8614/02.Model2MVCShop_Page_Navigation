package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Search search = new Search();

		// 판매상품관리 클릭시 초기페이지 1
		int currentPage = 1;

		// page 버튼 클릭시, listProduct.do
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		// search에 페이지관련 정보 넣어야하나??
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));

		int pageSize = Integer.parseInt(super.getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);

		System.out.println("컨트롤러에서의 " + search);
		// ==================================================================

		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getPurchaseList(search, user.getUserId());

		int pageUnit = Integer.parseInt(this.getServletContext().getInitParameter("pageUnit"));
		Page resultPage = new Page(currentPage, ((Integer) map.get("count")).intValue(), pageUnit, pageSize);

		
		// map1 : (String "count", Integer total)
		// map2 : (String "list", ArrayList<ProductVO> list)
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("list", map.get("list"));
		
		System.out.println("컨트롤러에서 resultPage : " + resultPage);
		System.out.println("컨트롤러에서 search : " + search);
		System.out.println("컨트롤러에서 list : " + map.get("list"));

		return "forward:/purchase/listPurchase.jsp";
	}

}

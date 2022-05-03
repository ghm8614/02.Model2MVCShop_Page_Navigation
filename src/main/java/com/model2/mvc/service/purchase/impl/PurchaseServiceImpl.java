package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;

public class PurchaseServiceImpl implements PurchaseService {

	PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}
	
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
	}
	

	@Override
	public Purchase getPurchase(int tranNo) throws Exception{
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception{
		return purchaseDAO.getPurchaseList(search, userId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) {
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception{
		purchaseDAO.updatePurchase(purchase);
	}
	
	@Override
	public void updateTranCode(Purchase purchase) throws Exception{
		purchaseDAO.updateTranCode(purchase);
	}
}

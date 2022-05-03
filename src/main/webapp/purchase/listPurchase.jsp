<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%--@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"--%>

<%@ page import="com.model2.mvc.service.domain.User"%>
<%@ page import="com.model2.mvc.service.domain.Purchase"%>
<%@ page import="java.util.List"%>
<%@ page import="com.model2.mvc.common.Search"%>
<%@ page import="com.model2.mvc.common.Page"%>


<%
User user = (User) session.getAttribute("user");

Search search = (Search) request.getAttribute("search");
Page resultPage = (Page)request.getAttribute("resultPage");
List<Purchase> list = (List<Purchase>) request.getAttribute("list");
System.out.println("jsp���� list : "+list);
%>

<%

//int totalPage = 0;

//int pageUnit = search.getPageSize();

//int currentPage = search.getCurrentPage();

//if (total > 0) {
//	totalPage = total / pageUnit;
//	if (total % pageUnit > 0) {
//		totalPage++;
//	}
//}
%>

<!-- �Ϲ�ȸ���� �����̷���ȸ -->

<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetPurchaseList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<!-- ��ü -->
		<form name="detailForm" action="/listPurchase.do" method="post">

			<!-- table 1 :  -->
			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">���� �����ȸ</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>


			<!-- table 2 : ���Ÿ�� ���̺� -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">��ü <%=resultPage.getTotalCount()%> �Ǽ�, ���� <%=resultPage.getCurrentPage()%> ������
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="50">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="120">�ֹ�����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">��ǰ��</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">�����ڸ�</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="200">�����ּ�</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="300">�����Ȳ</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�������</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>

				<%
				int count = 1;
				for (Purchase purchase : list) {
				%>
				<tr class="ct_list_pop">
					<td align="center"><a
						href="/getPurchase.do?tranNo=<%=purchase.getTranNo()%>"><%=count++%></a></td>
					<td></td>
					<td align="center"><%=purchase.getOrderDate()%></td>
					<td></td>
					<td align="left"><a
						href="/getProduct.do?prodNo=<%=purchase.getPurchaseProd().getProdNo()%>"><%=purchase.getPurchaseProd().getProdName()%></a>
					</td>
					<td></td>
					<td align="left"><%=purchase.getReceiverName()%></td>
					<td></td>
					<td align="left"><%=purchase.getDivyAddr()%></td>
					<td></td>

					<%
					if (purchase.getTranCode().equals("1")) {
					%>
					<td align="left">���ſϷ�</td>
					<td></td>
					<%
					} else if (purchase.getTranCode().equals("2")) {
					%>
					<td align="left"><a
						href="updateTranCode.do?tranNo=<%=purchase.getTranNo()%>&tranCode=3">���ǵ���</a></td>
					<td></td>
					<%
					} else if (purchase.getTranCode().equals("3")) {
					%>
					<td align="left">��ۿϷ�</td>
					<td></td>
					<%
					}
					%>


					<%
					if (purchase.getPaymentOption().equals("1")) {
					%>
					<td align="left">���ݰ���</td>
					<td></td>
					<%
					} else if (purchase.getPaymentOption().equals("2")) {
					%>
					<td align="left">�ſ����</td>
					<td></td>
					<%
					}
					}
					%>
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>


			<!-- PageNavigation Start... -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center"><input type="hidden" id="currentPage"
						name="currentPage" value="" /> <%
					 if (resultPage.getCurrentPage() <= resultPage.getPageUnit()) {
					 %> �� ���� <%
					 } else {
					 %> <a
						href="javascript:fncGetPurchaseList('<%=resultPage.getCurrentPage() - 1%>')">��
							����</a> <%
					 }
					 %> <%
					 for (int i = resultPage.getBeginUnitPage(); i <= resultPage.getEndUnitPage(); i++) {
					 %> <a href="javascript:fncGetPurchaseList('<%=i%>');"><%=i%></a> <%
					 }
					 %> <%
					 if (resultPage.getEndUnitPage() >= resultPage.getMaxPage()) {
					 %> ���� �� <%
					 } else {
					 %> <a
						href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage() + 1%>')">����
							��</a> <%
					 }
					 %></td>
				</tr>
			</table>


		</form>

	</div>

</body>

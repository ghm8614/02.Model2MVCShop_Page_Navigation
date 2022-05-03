<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>


<%@ page import="java.util.List"%>
<%@ page import="com.model2.mvc.service.domain.Product"%>
<%@ page import="com.model2.mvc.common.Search"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>


<%
String menu = (String) request.getAttribute("menu");
Search search = (Search) request.getAttribute("search");
Page resultPage = (Page) request.getAttribute("resultPage");
List<Product> list = (List<Product>) request.getAttribute("list");

//==> null �� ""(nullString)���� ����
String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%>


<html>
<head>
<title>��ǰ ����</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetProductList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

	<!-- ��ü -->
		<form name="detailForm" action="/listProduct.do?menu=<%=menu%>" method="post">


		<!-- table 1 : ��ǰ���� OR ��ǰ�����ȸ title -->
			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">

							<%
							if (menu != null && menu.equals("manage")) {
							%>
							<tr>
								<td width="93%" class="ct_ttl01">��ǰ ����</td>
							</tr>
							<%
							} else if (menu != null && menu.equals("search")) {
							%>
							<tr>
								<td width="93%" class="ct_ttl01">��ǰ ��� ��ȸ</td>
							</tr>
							<%
							}
							%>

						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>


		<!-- table 2 : searchCondition, searchKeyword, �˻� ��ư ���̺�-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>

				<!-- condition, keyword -->
					<td align="right"><select name="searchCondition"
						class="ct_input_g" style="width: 80px">
							<option value="0"
								<%=(searchCondition.equals("0") ? "selected" : "")%>>��ǰ��ȣ</option>
							<option value="1"
								<%=(searchCondition.equals("1") ? "selected" : "")%>>��ǰ��</option>
							<option value="2"
								<%=(searchCondition.equals("2") ? "selected" : "")%>>��ǰ����</option>
					</select> <input type="text" name="searchKeyword" value="<%=searchKeyword%>"
						class="ct_input_g" style="width: 200px; height: 19px" /></td>

				<!-- �˻���ư -->
					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;"><a
									href="javascript:fncGetProductList(1);">�˻�</a></td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


		<!-- table 3 : ��ǰ���� OR ��ǰ�����ȸ data ���̺� -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">��ü <%=resultPage.getTotalCount()%> �Ǽ�, ���� <%=resultPage.getCurrentPage()%>
						������
					</td>
				</tr>

				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">��ǰ��</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�������</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				
				<%
				int no = 1;
				for (Product product : list) {
				%>
				<tr class="ct_list_pop">
					<td align="center"><%=no++%></td>
					<td></td>

					<%
					// ������ �Ϲ����� ��� ����..
					// �˻���ư���� request�� listProductAction ���� menu = null -> ������ƮException 
					if (menu != null && menu.equals("search")) {
					%>
						<td align="left"><a
							href="/getProduct.do?prodNo=<%=product.getProdNo()%>&menu=search"><%=product.getProdName()%></a></td>
						<td></td>
						<td align="left"><%=product.getPrice()%></td>
						<td></td>
						<td align="left"><%=product.getManuDate()%></td>
						<td></td>
						
						<% if(product.getProTranCode()==null) {%>
							<td align="left">�Ǹ���</td>
						<%} else {%>
							<td align="left">������</td>
						<%}
					
					// 	������(admin)��
					} else if (menu != null && menu.equals("manage")) {
					%>
						<td align="left"><a
							href="/updateProductView.do?prodNo=<%=product.getProdNo()%>&menu=manage"><%=product.getProdName()%></a></td>
						<td></td>
						<td align="left"><%=product.getPrice()%></td>
						<td></td>
						<td align="left"><%=product.getManuDate()%></td>
						<td></td>
						
						<% if(product.getProTranCode()==null) {%>
							<td align="left">�Ǹ���</td>
						<%} else if(product.getProTranCode().startsWith("1")) {%>
							<td align="left">���ſϷ� <a href="/updateTranCodeByProd.do?prodNo=<%=product.getProdNo()%>&tranCode=2">����ϱ�</a></td>
						<%} else if(product.getProTranCode().startsWith("2")) {%>
							<td align="left">�����</td>
					<% }else if(product.getProTranCode().startsWith("3")){%>
						<td align="left">��ۿϷ�</td>
					<%} 
					}
					}%>	
						
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
			</table>


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
						href="javascript:fncGetProductList('<%=resultPage.getCurrentPage() - 1%>')">��
							����</a> <%
					 }
					 %> <%
					 for (int i = resultPage.getBeginUnitPage(); i <= resultPage.getEndUnitPage(); i++) {
					 %> <a href="javascript:fncGetProductList('<%=i%>');"><%=i%></a> <%
					 }
					 %> <%
					 if (resultPage.getEndUnitPage() >= resultPage.getMaxPage()) {
					 %> ���� �� <%
					 } else {
					 %> <a
						href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage() + 1%>')">����
							��</a> <%
					 }
					 %></td>
				</tr>
			</table>
			<!-- PageNavigation End... -->



		</form>

	</div>
</body>
</html>
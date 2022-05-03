<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%--@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"--%>


<%@ page import="com.model2.mvc.service.domain.Purchase" %>
<%@ page import="com.model2.mvc.service.domain.User" %>


<% Purchase purchase = (Purchase)request.getAttribute("purchase"); %>
<% User user = (User)session.getAttribute("user"); %>
 
<html>
<head>
<title>Insert title here</title>
</head>

<body>
<!-- ���� ��ư ���� ��, �������Ǵ� ���ſϷ� ���� ������ -->

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>

<!-- purchase �� User buyer, Product purchaseProd ���� -->
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%= purchase.getPurchaseProd().getProdNo()%></td>
		<td></td>
	</tr>
	
	<!-- ������ ���̵� �����ý�, 
		1. requestScope�� ����� purchase.buyer.userId
		2. sessionScope�� ����� user.userId 
		��� ����  -->
	<tr>
		<td>�����ھ��̵�</td>
		<td><%= user.getUserId() %></td>	
		<!--<td>${requestScope.purchase.buyer.userId}</td> -->	
		<td></td>
	</tr>
	
	
<!--  -->	
	<tr>
		<td>���Ź��</td>
		<td><%= purchase.getPaymentOption()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%= purchase.getReceiverName()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%= purchase.getReceiverPhone()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%= purchase.getDivyAddr()%></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%= purchase.getDivyRequest()%></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%= purchase.getDivyDate()%></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>
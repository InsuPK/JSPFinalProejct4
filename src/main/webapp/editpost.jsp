<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.example.jspfinalproejct4.dao.BoardDAO, com.example.jspfinalproejct4.util.FileUpload"%>
<%@ page import="com.example.jspfinalproejct4.bean.BoardVO" %>

<% request.setCharacterEncoding("utf-8"); %>

<jsp:useBean id="u" class="com.example.jspfinalproejct4.bean.BoardVO" />
<jsp:setProperty property="*" name="u"/>

<%
	BoardDAO boardDAO = new BoardDAO();
	FileUpload upload = new FileUpload();
	BoardVO U = upload.uploadPhoto(request);
	int i=boardDAO.updateBoard(U);
	response.sendRedirect("posts.jsp");
	String msg = "데이터 추가 성공 !";
	if(i == 0) msg = "[에러] 데이터 추가 ";
%>

<script>
	alert('<%=msg%>');
	location.href='posts.jsp';
</script>
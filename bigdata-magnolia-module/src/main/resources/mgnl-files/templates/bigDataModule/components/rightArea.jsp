<%--@elvariable id="components" type="java.util.Collection"--%>
<%@ include file="../includes/taglibs.jsp" %>
<div>
  <c:forEach items="${components}" var="component">
    <cms:component content="${component}" />
  </c:forEach>
</div>

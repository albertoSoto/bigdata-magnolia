<%--@elvariable id="content" type="info.magnolia.jcr.util.ContentMap"--%>
<%--@elvariable id="damfn" type="info.magnolia.dam.templating.functions.DamTemplatingFunctions"--%>
<%@ include file="../includes/taglibs.jsp" %>
<div class="row">
    <h1>${content.heading}</h1>
    <c:if test="${not empty content.photo}">
        <%-- Using JSP 2.2 method calling from EL - requires Servlet 3.0 (Tomcat 7) --%>
        <%-- On previous versions this exception is thrown: The function getAssetLink must be used with a prefix when a default namespace is not specified --%>
        <img style="float: right;margin: 10px;" src="${damfn.getAssetLink(content.photo)}"/>
    </c:if>
    <p>${cmsfn:decode(content).body}</p>
</div>
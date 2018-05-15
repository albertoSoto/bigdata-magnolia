<%--@elvariable id="comments" type="java.util.Collection<com.albertosoto.magnolia.bigdata.templates.components.Comment>"--%>
<%@ include file="../includes/taglibs.jsp"%>

<script type="text/javascript">
    function deleteComment(id) {
        document.location = '?action=delete&id=' + id;
    }
</script>

<form action="?">
<input type="hidden" name="action" value="add" />
<blossom:pecid-input />
<table>
    <tr>
        <td>
            Comment<br />
            <textarea name="text" rows="7" cols="40"></textarea>
        </td>
        <td>&nbsp;</td>
        <td valign="top">
            Name<br />
            <input type="text" name="name" /><br />
            E-Mail<br />
            <input type="text" name="email" /><br />
            <br/>
            <input type="submit" value="Post comment" />
        </td>
    </tr>
</table>
</form>

<br/>

<c:forEach items="${comments}" var="comment">
<div class="comment">
    <div style="background-color:#f0f0f0;color:#191919;padding:5px;border:1px solid #e0e0e0;">
        <div style="float:left;font-weight:bold;">${comment.name}</div>
        <div style="float:right;"><fmt:formatDate value="${comment.created}" dateStyle="full" /></div>
        <div style="clear:both;"></div>
    </div>
    <div style="padding: 7px;">${comment.text}</div>
    <div style="padding: 7px;text-align:right;"><input type="button" value="Delete" onclick="deleteComment('${comment.id}');" /></div>
</div>
</c:forEach>

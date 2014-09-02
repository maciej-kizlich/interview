<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>


<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<form role="form" name="configs" id="settingsForm" class="form-horizontal" action="<c:url value="/admin/save" />" method="POST">
			<c:forEach items="${configs}" var="entry">
				<div class="form-group">
					<div class="col-sm-5">
						${entry.description} (${entry.key}) <input value="${entry.value}" name="${entry.key}" class="form-control" id="input">
					</div>
				</div>
			</c:forEach>
   		  
   		    <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Save</button>
                </div>
            </div>

		</form>

	</tiles:putAttribute>
</tiles:insertDefinition>

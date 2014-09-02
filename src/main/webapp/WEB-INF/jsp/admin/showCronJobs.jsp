<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

	   <spring:url value="/admin/execute/" var="cronExecuteUrl" htmlEscape="true" />


       Available Cron jobs:
        <hr />

		<form role="form" name="jobs" id="jobsForm" class="form-horizontal"
			action="${cronExecuteUrl}" method="POST">

			<c:forEach items="${jobs}" var="entry">
				
				${entry.key} 
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" onClick="submitJobName(this); this.disabled=true;"
							value="${entry.key}" class="btn btn-default">Execute Job</button>
					</div>
				</div>

			</c:forEach>

			<input type="hidden" value="" name="jobName" id="hiddenInput" />

		</form>

		<script>
    function submitJobName(object) {
    	 document.getElementById("hiddenInput").setAttribute("value", object.value);
        	document.getElementById("jobsForm").submit();
    }
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
    
    
    <div class="panel panel-warning">
  <div class="panel-heading"><b>Pytanie:</b></div>
  <div class="panel-body">
   <b>${question.question}</b>
  </div>
</div>

        <datatables:table id="answers" data="${answers}" cdn="true" row="answer" theme="bootstrap3"
                          cssClass="table table-striped table-hover" filterable="false" >
            <datatables:column title="Odpowiedź" cssClass="success">
                   <c:out value="${answer.answer}"></c:out>
            </datatables:column>
            
            <datatables:column title="Odpowiedział" cssClass="success">
                ${answer.user.username}
            </datatables:column>
            
            <datatables:column title="Ocena" cssClass="success">
                ${answer.rating}
            </datatables:column>
            
             <datatables:column title="Oceń" cssClass="success">		
			<button class="btn btn-success btn-default">
  				  <span id="up" class="glyphicon glyphicon-thumbs-up" style="vertical-align:middle"></span>  
 			 </button>
 			 
 			 	<button class="btn btn-success btn-default">
  				  <span class="glyphicon glyphicon-thumbs-down" style="vertical-align:middle"></span>  
 			 </button>
 </datatables:column>
		</datatables:table>

    </tiles:putAttribute>
</tiles:insertDefinition>
	<script>
    function vote(object) {
    	 document.getElementById("hiddenInput").setAttribute("value", object.value);
        	document.getElementById("jobsForm").submit();
    }
</script>
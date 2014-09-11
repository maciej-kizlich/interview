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

        <datatables:table id="messages" data="${messages}" cdn="true" row="message" theme="bootstrap3"
                          cssClass="table table-striped table-hover" filterable="false">
            <datatables:column title="Temat" cssClass="success" >
           <div id="topic1">
				${message.topic}
				</div>
            </datatables:column>
            <datatables:column title="Treść wiadomości" cssClass="success max-width: 400px" styleClass="wrap">
                ${message.message}
            </datatables:column>
            
             <datatables:column title="Od" cssClass="success">
             
                <spring:url value="/user/showDetails/${message.fromUser.id}" var="userUrl" htmlEscape="true">
                    <spring:param name="userId" value="${message.fromUser.id}"/>
                </spring:url>
              <a href="${fn:escapeXml(userUrl)}" id="receiver"><c:out value="${message.fromUser.username}"></c:out></a>
            </datatables:column>
            
                 <datatables:column title="" cssClass="success">
                   <button type="reset" class="btn btn-success btn-sm pull-right" onClick="reply(this);">Odpowiedz
                        <span class="glyphicon glyphicon-share-alt" ></span>  
                    </button>
            </datatables:column>
		</datatables:table>
		
		    <form role="form" name="msg" id="userForm" class="form-horizontal" action="<c:url value="/user/sendMessage" />" method="POST">
            <div class="form-group">
                <label for="input1" class="col-sm-2 control-label">Temat</label>
                <div class="col-sm-5">
                    <input value="" name="topic" class="form-control" id="input1">
                </div>
            </div>
            <div class="form-group">
                <label for="input2" class="col-sm-2 control-label">Odbiorca</label>
                 <div class="col-sm-5">
                    <input name="receiver" value="" class="form-control" id="input3" >
                </div>
            </div>
            <div class="form-group">
                <label for="input3" class="col-sm-2 control-label">Treść wiadomości</label>
                               <div class="col-sm-5 col-md-5 col-sm-5">
                    <textarea id="input2" name="body" class="form-control" rows="10"></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" value="">
                    <button type="submit" onClick="this.disabled=true;" class="btn btn-success btn-sm">Wyślij</button>
                </div>
            </div>
        </form>

<script>
    function reply(object) {
    	 var topic = "RE: " + document.getElementById("topic1").innerHTML.trim();
    	 document.getElementById("input1").setAttribute("value", topic);
    	 
    	 var receiver = document.getElementById("receiver").innerHTML;
    	 document.getElementById("input3").setAttribute("value", receiver.trim());
    }
</script>

    </tiles:putAttribute>
</tiles:insertDefinition>


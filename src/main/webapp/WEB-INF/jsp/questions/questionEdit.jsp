\<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <form role="form" name="editQuestion" class="form-horizontal" action="<c:url value="/questions/save" />" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <label for="inputl" class="col-sm-2 control-label">Nazwa firmy</label>
                <div class="col-sm-5">
                    <input value="${question.companyName}" name="companyName" class="form-control" id="inputl" >
                </div>
            </div>
            <div class="form-group">
                <label for="input2" class="col-sm-2 control-label">Stanowisko</label>
                <div class="col-sm-5">
                    <input name="position" value="${question.position}" class="form-control" id="input2" >
                </div>
            </div>
            <div class="form-group">
                <label for="input3" class="col-sm-2 control-label">Pytanie</label>
              
          <div class="col-sm-5 col-md-5 col-sm-5">
                    <textarea id="input2" name="question" class="form-control" rows="9"></textarea>
                </div>
            </div>

           <div class="form-group">
                <label for="input4" class="col-sm-2 control-label">Odpowiedź</label>
          <div class="col-sm-5 col-md-5 col-sm-5">
                    <textarea id="input2" name="answer" class="form-control" rows="9"></textarea>
                </div>
            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${question.questionId}">
                    
                    <button type="submit" class="btn btn-success btn-sm">Dodaj
                    <span class="glyphicon glyphicon-ok" style="vertical-align:middle;"></span>  
                    </button>
                   
                   
                    <button type="reset" class="btn btn-danger btn-sm">Wyczyść
                        <span class="glyphicon glyphicon-remove" style="vertical-align:middle;"></span>  
                    </button>
                </div>
            </div>
        </form>
    </tiles:putAttribute>
</tiles:insertDefinition>

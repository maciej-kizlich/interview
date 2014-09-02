<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <form role="form" name="editBook" class="form-horizontal" action="<c:url value="/books/save" />" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <label for="inputl" class="col-sm-2 control-label">Book title</label>
                <div class="col-sm-5">
                    <input value="${book.title}" name="title" class="form-control" id="inputl" placeholder="title">
                </div>
            </div>
            <div class="form-group">
                <label for="input2" class="col-sm-2 control-label">Book author</label>
                <div class="col-sm-5">
                    <input name="author" value="${book.author}" class="form-control" id="input2" placeholder="author">
                </div>
            </div>
            <div class="form-group">
                <label for="input3" class="col-sm-2 control-label">Book description</label>
                <div class="col-sm-5">
                    <input name="description" value="${book.description}" class="form-control" id="input3" placeholder="description">
                </div>
            </div>
            <div class="form-group">
                <label for="input4" class="col-sm-2 control-label">Book quantity</label>
                <div class="col-sm-5">
                    <input name="availableQuantity" value="${book.availableQuantity}" class="form-control" id="input4" placeholder="quantity">
                </div>
            </div>
            <div class="form-group">
                <label for="input4" class="col-sm-2 control-label">Book image</label>
                <div class="col-sm-5">
                    <input type="file" name="file" id="input5" placeholder="image">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${book.id}">
                    <button type="submit" class="btn btn-default">Save</button>
                </div>
            </div>
        </form>
    </tiles:putAttribute>
</tiles:insertDefinition>

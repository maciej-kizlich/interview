<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div style="float: right; padding-left: 15px">
            <img id="heart"/>
        </div>

        Book details:
        <hr />

        Title: ${fn:escapeXml(data.book.title)} <br/>
        Author: ${fn:escapeXml(data.book.author)} <br/>
        Description:<br/>
        ${fn:escapeXml(data.book.description)}<br/>
        <c:if test="${data.book.image!=null}">
            <img src="data:image/jpg;base64,<c:out value='${data.book.imageString}'/>" /><br/>
        </c:if>
        Available: ${data.book.availableQuantity}<br/>
        Rating: ${data.rating}

        <hr/>

        <div style="float: none">
            <div style="float: left">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <spring:url value="/books/edit/{bookId}" var="bookEditUrl" htmlEscape="true">
                        <spring:param name="bookId" value="${data.book.id}"/>
                    </spring:url>

                    <form action="${bookEditUrl}" >
                        <input type="hidden" value="${data.book.id}" />
                        <input class="btn btn-default" type="submit" value="Edit"/>
                    </form>
                </sec:authorize>
            </div>
            <div style="float: left; padding-left: 15px">
                <spring:url value="/books/reserve" var="bookReserveUrl" htmlEscape="true">
                    <spring:param name="bookId" value="${data.book.id}"/>
                </spring:url>

                <form:form method="POST" action="${bookReserveUrl}" >
                    <input type="hidden" value="${data.book.id}" name="bookId"/>
                    <input class="btn btn-default" type="submit" value="Reserve"/>
                </form:form>
            </div>
        </div>

        <div style="clear: both; float: none" >
            <input id="starRating" name="rateValue" value="${data.rating}" type="number" class="rating" min=0 max=5 step=1 data-size="xs">
        </div>
        <div style="clear: both; float: none">
            <form action="/books/feedback" method="POST" onsubmit="return validateFeedbackForm();">
                <div>
                    <label style="font-size:14px; color: #555555">Leave your feedback</label>
                    <input type="hidden" value="${data.book.id}" name="bookId" />
                    <input id="msg" type="text" style="width: 530px" name="message" />
                    <input class="btn btn-default" type="submit" value="Commit"/>
                </div>
            </form>
        </div>

        <datatables:table id="books" pageable="false" autoWidth="false" data="${data.feedbackList}" sortable="false" cdn="true" theme="bootstrap3" row="fdb" cssClass="table" filterable="false">
            <datatables:column>
                <spring:url value="/user/showDetails/${fdb.user.id}" var="userUrl" htmlEscape="true"/>
                <div>
                    ${fdb.message}
                </div>
                <div align="right">
                    <a href="${fn:escapeXml(userUrl)}"><c:out value="${fdb.user.username}"></c:out></a><br/>
                    <h5>${fdb.createdDate}</h5>
                </div>
            </datatables:column>
        </datatables:table>
        <script>
            var isFavorite = ${data.favorite};
            changeFavoriteIcon();
            function changeFavoriteIcon() {
                if (isFavorite == 0) {
                    document.getElementById('heart').src = "/resources/image/heart-black.png";
                    document.getElementById('heart').title = "Make as favorite book";
                } else {
                    document.getElementById('heart').src = "/resources/image/heart-red.png";
                    document.getElementById('heart').title = "Remove from favorites";
                }
            }

            $('#heart').on('click', function(event, value) {
                if (isFavorite == 0) {
                    isFavorite = 1;
                    $.post("/books/addFavorite", {bookId:${data.book.id}})
                } else {
                    isFavorite = 0;
                    $.post("/books/removeFavorite", {bookId:${data.book.id}})
                }
                changeFavoriteIcon();
            });

            $('#starRating').on('rating.change', function(event, value, caption) {
                $.post("/books/evaluate", {rateValue:value, bookId:${data.book.id}})
            });

            function validateFeedbackForm() {
                var message = $("#msg").val();
                if (message.length === 0 || !message.trim()) {
                    alert("Feedback is empty");
                    return false;
                }
                return true;
            }
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>

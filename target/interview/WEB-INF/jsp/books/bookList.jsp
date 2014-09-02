<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div style="float: none">
            <div style="float: left">
                <h4><u>Top Read Books</u></h4>
                <c:set var="count" value="0" scope="page" />
                <c:forEach items="${mostReadBooks}" var="mostReadBook">
                    <spring:url value="/books/get/{bookId}" var="bookUrl" htmlEscape="true">
                        <spring:param name="bookId" value="${mostReadBook.book.id}"/>
                    </spring:url>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    ${count}. <a href="${fn:escapeXml(bookUrl)}"><c:out value="${mostReadBook.book.title}"></c:out></a> (${mostReadBook.value})</br>
                </c:forEach>
                </br>
            </div>

            <div style="float: left; padding-left: 100px">
                <h4><u>Top Rated Books</u></h4>
                <c:set var="count" value="0" scope="page" />
                <c:forEach items="${mostRatedBooks}" var="mostRatedBook">
                    <spring:url value="/books/get/{bookId}" var="bookUrl" htmlEscape="true">
                        <spring:param name="bookId" value="${mostRatedBook.book.id}"/>
                    </spring:url>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    ${count}. <a href="${fn:escapeXml(bookUrl)}"><c:out value="${mostRatedBook.book.title}"></c:out></a> (${mostRatedBook.value})</br>
                </c:forEach>
                </br>
            </div>
            <div style="float: right">
                <a href="#" onclick="changeSearchDivState()"><c:out value="Search"></c:out></a>
                <div id="searchDiv" style="display:none">
                    <jsp:include page="bookSearch.jsp"/>
                </div>
            </div>
        </div>

        <datatables:table id="books" data="${books.bookList}" cdn="true" row="bookData" theme="bootstrap3" cssClass="table table-striped" filterable="false">
            <datatables:column title="Title">
                <spring:url value="/books/get/{bookId}" var="bookUrl" htmlEscape="true">
                    <spring:param name="bookId" value="${bookData.book.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(bookUrl)}"><c:out value="${bookData.book.title}"></c:out></a>
            </datatables:column>
            <datatables:column title="Author">
                <c:out value="${bookData.book.author}"/>
            </datatables:column>
            <datatables:column title="Rating">
                <c:out value="${bookData.rating}"/> star
            </datatables:column>
                <spring:url value="/books/delete" var="deleteUrl" htmlEscape="true">
                    </spring:url>
            <datatables:column title="Delete">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <form:form method="GET" action="${fn:escapeXml(deleteUrl)}" >
                        <input type="hidden" value="${bookData.book.id}" name="bookId"/>
                        <input class="btn btn-primary" type="submit" value="Delete"/>
                    </form:form>
                </sec:authorize>
            </datatables:column>
        </datatables:table>

        <script>
            function changeSearchDivState() {
                var current = document.getElementById("searchDiv").style.display;
                if (current == "none") {
                    document.getElementById("searchDiv").style.display = "block";
                } else {
                    document.getElementById("searchDiv").style.display = "none";
                }
            }
        </script>

    </tiles:putAttribute>
</tiles:insertDefinition>

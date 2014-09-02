<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">



        You have canceled a reservation!
        <br/>
        Book details: <br/>
        Title: ${order.book.title}<br/>
        Author: ${order.book.author}<br/>

        <br/></br>

<a href="<spring:url value="/books/list" htmlEscape="true" />"> Go to the list of books</a>

    </tiles:putAttribute>
</tiles:insertDefinition>


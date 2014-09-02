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

        <datatables:table id="books" data="${orders}" cdn="true" row="order" theme="bootstrap3"
                          cssClass="table table-striped" filterable="false">
            <datatables:column title="Book title">
                <spring:url value="/books/get/${order.book.id}" var="bookUrl" htmlEscape="true">
                    <spring:param name="bookId" value="${order.book.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(bookUrl)}"><c:out value="${order.book.title}"></c:out></a>
            </datatables:column>
            <datatables:column title="User">
                ${order.user.username}
            </datatables:column>
            <datatables:column title="Date of reservation">
                <fmt:formatDate value="${order.reservationDate}" type="BOTH" dateStyle="FULL" timeStyle="SHORT"/>
            </datatables:column>
            <datatables:column title="Expected date of return">
                <fmt:formatDate value="${order.expectedReturnDate}" dateStyle="MEDIUM"/>
            </datatables:column>
            <datatables:column title="Date returned">
                <fmt:formatDate value="${order.returnedDate}" type="BOTH" dateStyle="MEDIUM" timeStyle="SHORT"/>
            </datatables:column>
            <datatables:column title="Status">
                ${order.status}
            </datatables:column>

     <spring:url value="/orders/cancelOrder" var="cancelOrderUrl" htmlEscape="true">
     </spring:url>
                
     <spring:url value="/books/borrow" var="bookBorrowUrl" htmlEscape="true">
     </spring:url>
     
     <spring:url value="/books/return" var="bookReturnUrl" htmlEscape="true">
     </spring:url>

		<datatables:column title="Actions">

			<c:if test="${order.status=='RESERVED'}">
			
			      <form:form method="POST" action="${fn:escapeXml(cancelOrderUrl)}" >
                  <input type="hidden" value="${order.id}" name="orderId"/>
                  <input class="btn btn-danger" type="submit" value="Cancel"/>
           		  </form:form>
			
			</c:if>
				
		    <sec:authorize access="hasRole('ROLE_ADMIN')">

			<c:if test="${order.status=='RESERVED'}">
			     
			      <form:form method="POST" action="${fn:escapeXml(bookBorrowUrl)}" >
                  <input type="hidden" value="${order.user.id}" name="borrowingUserId"/>
                  <input type="hidden" value="${order.id}" name="orderId"/>
                  <input class="btn btn-danger" type="submit" value="Borrow"/>
           		  </form:form>
			    
			</c:if>
			<c:if test="${order.status=='BORROWED'}">
			
			    <form:form method="POST" action="${fn:escapeXml(bookReturnUrl)}" >
                  <input type="hidden" value="${order.book.id}" name="bookId"/>
                  <input type="hidden" value="${order.id}" name="orderId"/>
                  <input class="btn btn-danger" type="submit" value="Return"/>
           		  </form:form>
			</c:if>
				
		   </sec:authorize>

		</datatables:column>
		</datatables:table>

    </tiles:putAttribute>
</tiles:insertDefinition>


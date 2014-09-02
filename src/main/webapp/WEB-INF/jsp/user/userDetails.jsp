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

        <div style="float: none">
            <div style="float: left">
                <img src="/resources/image/default-user-image.jpg" height="150px" width="150px" title="Profile picture"/>
            </div>
            <div style="float: left; padding-left: 30px">
                User details:
                <hr />
                User name: ${user.username} <br/>
                Last login date: ${user.lastLoginDate} <br/>
                Registration date: ${user.userCreationDate} <br/>
            </div>
        </div>

        <div style="clear: both; float: none">
            <div style="float: right">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <spring:url value="/user/edit/{userId}" var="userEditUrl" htmlEscape="true">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <form action="${userEditUrl}" >
                        <input type="hidden" value="${user.id}" />
                        <input class="btn btn-default" type="submit" value="Edit"/>
                    </form>
                </sec:authorize>
            </div>
            <div>
                <div>
                    <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                        <li class="active"><a href="#feedback" data-toggle="tab">Feedback</a></li>
                        <li><a href="#favorite" data-toggle="tab">Favorites</a></li>
                        <li><a href="#ads" data-toggle="tab">Ads</a></li>
                    </ul>
                    <div id="my-tab-content" class="tab-content">
                        <div class="tab-pane active" id="feedback">
                            <!-- User's feedback list -->
                            <datatables:table id="feedbackTbl" pageable="false" autoWidth="false" data="${feedback}" sortable="false" cdn="true" theme="bootstrap3" row="b" cssClass="table" filterable="false">
                                <datatables:column>
                                    <spring:url value="/books/get/${b.book.id}" var="bookUrl" htmlEscape="true"/>
                                    <div>
                                            ${b.message}
                                    </div>
                                    <div align="right">
                                        Commented on book <a href="${fn:escapeXml(bookUrl)}"><c:out value="${b.book.title}"></c:out></a> by ${b.book.author}<br/>
                                        <h5>${b.createdDate}</h5>
                                    </div>
                                </datatables:column>
                            </datatables:table>
                        </div>
                        <div class="tab-pane" id="favorite">
                            <!-- User's feedback list -->
                            <datatables:table id="favoriteTbl" pageable="false" autoWidth="false" data="${favorites}" sortable="false" cdn="true" theme="bootstrap3" row="f" cssClass="table" filterable="false">
                                <datatables:column title="Title">
                                    <spring:url value="/books/get/${f.book.id}" var="bookUrl" htmlEscape="true"/>
                                        <a href="${fn:escapeXml(bookUrl)}"><c:out value="${f.book.title}"></c:out></a>
                                </datatables:column>
                                <datatables:column title="Autor">
                                    ${f.book.author}
                                </datatables:column>
                                <datatables:column title="Date">
                                    ${f.createdDate}
                                </datatables:column>
                            </datatables:table>
                        </div>
                        <div class="tab-pane" id="ads">
                            Your Ad Could Be Here
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>

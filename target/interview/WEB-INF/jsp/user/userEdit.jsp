<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>


<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <form role="form" name="user" id="userForm" class="form-horizontal" action="<c:url value="/user/save" />" method="POST">
            <div class="form-group">
                <label for="input1" class="col-sm-2 control-label">User name</label>
                <div class="col-sm-5">
                    <input value="${user.username}" name="username" class="form-control" id="input1" placeholder="user name">
                </div>
            </div>
            <div class="form-group">
                <label for="input2" class="col-sm-2 control-label">User roles</label>
                <div class="col-sm-5">
                    <c:set var="role_admin_select" value="false" />
                    <c:set var="role_user_select" value="false" />
                    <c:forEach items="${user.authorities}" var="currentItem" varStatus="stat">
                        <c:if test="${currentItem.authority eq \"ROLE_ADMIN\"}">
                            <c:set var="role_admin_select" value="true" />
                        </c:if>
                        <c:if test="${currentItem.authority eq \"ROLE_USER\"}">
                            <c:set var="role_user_select" value="true" />
                        </c:if>
                    </c:forEach>

                    <select id="input2" class="multiselect" name="stringAuthorities" multiple="multiple">
                        <option <c:if test="${role_user_select}">selected="selected"</c:if> value="ROLE_USER">User</option>
                        <option <c:if test="${role_admin_select}">selected="selected"</c:if> value="ROLE_ADMIN">Admin</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="input3" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-5">
                    <input name="password" value="" class="form-control" id="input3" placeholder="enter new password">
                </div>
            </div>
              <div class="form-group">
                <label for="input4" class="col-sm-2 control-label">Re-type password</label>
                <div class="col-sm-5">
                    <input name="repassword" value="" class="form-control" id="input4" placeholder="re-type password">
                </div>
            </div>
            <div class="form-group">
                <label for="input5" class="col-sm-2 control-label">Enabled</label>
                <div class="col-sm-5">
					<f:checkbox path="user.enabled" value="${user.enabled}"/>
                </div>
            </div>
             <div class="form-group">
                <label for="input5" class="col-sm-2 control-label">Reservations blocked</label>
                <div class="col-sm-5">
					<f:checkbox path="user.reservationBlocked" value="${user.reservationBlocked}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" class="btn btn-default">Save</button>
                </div>
            </div>
        </form>



        <!-- Initialize the plugin: -->
        <script type="text/javascript">
            $(document).ready(function() {
            $('#userForm')
                    .find('[name="stringAuthorities"]')
                    .multiselect()
                    .end()
                    .bootstrapValidator({
                        // Exclude only disabled fields
                        // The invisible fields set by Bootstrap Multiselect must be validated
                        excluded: ':disabled',
                        feedbackIcons: {
                            valid: 'glyphicon glyphicon-ok',
                            invalid: 'glyphicon glyphicon-remove',
                            validating: 'glyphicon glyphicon-refresh'
                        },
                        fields: {
                            stringAuthorities: {
                                validators: {
                                    callback: {
                                        message: 'Please choose at least one role',
                                        callback: function(value, validator) {
                                            // Get the selected options
                                            var options = validator.getFieldElements('stringAuthorities').val();
                                            return (options != null
                                                    && options.length >= 1);
                                        }
                                    }
                                }
                            }
                        }
                    });
            });
        </script>

    </tiles:putAttribute>
</tiles:insertDefinition>

<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>
<h2>Hello World!</h2>
${helloString}

<c:forEach var = "info" items = "${matches}">
    <br><fmt:formatDate value="${info.matchDate.time}" type="date" />
        <c:out value = "${info.homeCommand} - ${info.guestCommand}" />
        <c:out value = "${info.score}" />
</c:forEach>

</body>
</html>

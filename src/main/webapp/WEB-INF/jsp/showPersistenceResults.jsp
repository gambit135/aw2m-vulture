<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Errors</h1>
        <p>
            This was passed in from the controller thus showing that
            the controller was accessed before the page was rendered.
            This is MVC (Model View Controller) in action.
            <%
                HashMap<String, String> map = (HashMap<String, String>) request.getSession().getAttribute("map");

                out.println((String) map.get("test"));
            %>
        </p>
        <p>
            Output from persistence: <br> A- 
            <%


                out.println((String) map.get("artists"));
            %>
            <br>
        </p>
        <p> 
            <%


                out.println((String) map.get("errors"));
            %>
            E- ${errors}
        </p>
    </body>
</html>
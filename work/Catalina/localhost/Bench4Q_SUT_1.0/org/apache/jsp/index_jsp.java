package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.DriverManager;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\r\n");
      out.write("<title>Bench4Q</title>\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\">\r\n");
      out.write("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">\r\n");
      out.write("<meta http-equiv=\"description\" content=\"This is my page\">\r\n");
      out.write("<!--\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\r\n");
      out.write("\t-->\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body text=\"#000000\" bgcolor=\"#FFFFFF\" link=\"#0000EE\" vlink=\"#551A8B\"\r\n");
      out.write("\talink=\"#FF0000\">\r\n");
      out.write("\r\n");
      out.write("\t<center>\r\n");
      out.write("\t\t<br> <br> <br>\r\n");
      out.write("\t\t<h2>Welcome to Bench4Q test!</h2>\r\n");
      out.write("\t\t<br> <br>\r\n");
      out.write("\t\t<h1>Bench4Q version 1.0</h1>\r\n");
      out.write("\t\t<br> <br>\r\n");
      out.write("\t\t<h2>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<a href=\"/Bench4Q_SUT_1.0/home\">Bench4Q' Book Store </a>\r\n");
      out.write("\t\t</h2>\r\n");
      out.write("\t\t<h3>\r\n");
      out.write("\t\t\tThis Book Store web application is based on the TPC-W benchmark\r\n");
      out.write("\t\t\t(version 1.8). <br> <br> <br> <br> <br> <br>\r\n");
      out.write("\t\t\t<br> Bench4Q is a QoS oriented B2C benchmark for Internetware\r\n");
      out.write("\t\t\tMiddleware. It makes many extensions of TPC-W for Internetware\r\n");
      out.write("\t\t\tfeatures, especially for load simulation and metrics analysis of a\r\n");
      out.write("\t\t\tbenchmark. <br> <a href=\"http://forge.ow2.org/projects/jaspte\">http://forge.ow2.org/projects/jaspte</a>\r\n");
      out.write("\t\t\t<br> <br> <br> <br>\r\n");
      out.write("\t\t\t----------------------------------------------------------------------------------------\r\n");
      out.write("\t\t\t<br> distributed according to the GNU Lesser General Public\r\n");
      out.write("\t\t\tLicense. <br>\r\n");
      out.write("\t\t</h3>\r\n");
      out.write("\r\n");
      out.write("\t</center>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

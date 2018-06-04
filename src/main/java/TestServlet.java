import utils.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by zjgz on 2018/5/25.
 * urlPatterns url匹配path  设置所匹配path才会进入到这
 */
@WebServlet(name = "TestServlet", urlPatterns = {ConstantUtil.ADD_DEVELOPER_URL, ConstantUtil.ALL_DEVELOPERS_URL, ConstantUtil.DELETE_DEVELOPER_URL, ConstantUtil.QUERY_DEVELOPER_URL, ConstantUtil.UPDATE_DEVELOPER_URL})
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
//
//        PrintWriter printWriter = response.getWriter();
//        printWriter.print("<h3>首个接口呀hello....<h3>");
        CommonModel commonModel = new CommonModel();
        String url = request.getRequestURI();
        if (url != null) {
            switch (url) {
                case ConstantUtil.ADD_DEVELOPER_URL:
                    addPerson(request, commonModel);
                    break;
                case ConstantUtil.ALL_DEVELOPERS_URL:
                    searchPersons(request, commonModel);
                    break;
                case ConstantUtil.UPDATE_DEVELOPER_URL:
                    updatePerson(request, commonModel);
                    break;
                case ConstantUtil.DELETE_DEVELOPER_URL:
                    deletePerson(request, commonModel);
                    break;
            }
        } else {
            commonModel.setFail();
        }


        PrintWriter printWriter = response.getWriter();
        printWriter.print(GsonUtil.bean2Json(commonModel));
    }

    private void addPerson(HttpServletRequest request, CommonModel commonModel) {
        //添加
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String sex = request.getParameter("sex");
        DeveloperBusiness business = new DeveloperBusiness();
        Person person = new Person();
        person.setName(name);
        if (!TextUtils.isEmpty(age)) {
            person.setAge(Integer.parseInt(age));
        } else {
            commonModel.setErrorMsg("年龄不能为null");
        }
        if (!TextUtils.isEmpty(sex)) {
            person.setSex(Integer.parseInt(sex));
        } else {
        }
        business.addDeveloper(person);
        commonModel.setSuccess();
    }

    private void searchPersons(HttpServletRequest request, CommonModel commonModel) {
        //添加
        DeveloperBusiness business = new DeveloperBusiness();

        List<Person> persons = business.getAllDevelopers();
        commonModel.setData(persons);
        commonModel.setSuccess();
    }

    private void updatePerson(HttpServletRequest request, CommonModel commonModel) {
        //添加
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String sex = request.getParameter("sex");
        DeveloperBusiness business = new DeveloperBusiness();
        business.updateDeveloper(id, name);
        commonModel.setSuccess();
    }

    private void deletePerson(HttpServletRequest request, CommonModel commonModel) {
        //添加
        String id = request.getParameter("id");
        DeveloperBusiness business = new DeveloperBusiness();
        business.deleteDeveloper(id);
        commonModel.setSuccess();
    }
}

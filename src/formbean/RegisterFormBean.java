package formbean;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 16:35 2017/12/12
 * @Modified By:
 */
public class RegisterFormBean {
    //RegisterFormBean中的属性与register.jsp中的表单输入项name一一对应
    private String username;
    private String password;
    private String confirmPwd;
    private String email;
    private String birthday;

    //存储校验不通过时给用户的错误提示信息
    private Map<String,String> errors = new HashMap<>();

    public Map<String,String> getErrors(){
        return errors;
    }

    public void setErrors(Map<String,String> errors){
        this.errors = errors;
    }

    /**
     * validate方法负责校验表单输入项
     * 表单输入项校验规则：
     *  private String username:用户名不能为空，并且要是3-8的字母
     *  private String password:密码不能为空，并且要是3-8的数字
     *  private String email:可以为空，不为空要求时一个合法的邮箱
     *  private String birthday:可以为空，不为空时，要是一个合法的日期
     */
    public boolean validate(){
        boolean isOk = true;

        if(this.username==null||this.username.trim().equals("")){
            isOk = false;
            errors.put("username","用户名不能为空！");
        }else{
            if(!this.username.matches("[a-zA-Z]{3,8}")){
                isOk = false;
                errors.put("username","用户名必须是3-8位的字母！");
            }
        }

        if(this.password==null||this.password.trim().equals("")){
            isOk = false;
            errors.put("password","密码不能为空!");
        }else{
            if(!this.password.matches("\\d{3,8}")){
                isOk = false;
                errors.put("password","密码必须是3-8位的数字！");
            }
        }

        if(this.confirmPwd != null){
            if(!this.confirmPwd.equals(this.password)){
                isOk = false;
                errors.put("confirmPwd","两次密码不一致！");
            }
        }

        if(this.email!=null&&!this.email.trim().equals("")){
            if(!this.email.matches("\\w+@\\w+(\\.\\w+)+")){
                isOk = false;
                errors.put("email","邮箱不是一个合法的邮箱！");
            }
        }

        if(this.birthday!=null&&!this.birthday.trim().equals("")){
            try{
                DateLocaleConverter conver = new DateLocaleConverter();
                conver.convert(this.birthday);
            }catch(Exception e){
                isOk = false;
                errors.put("birthday","生日必须要是一个日期！");
            }
        }
        return isOk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

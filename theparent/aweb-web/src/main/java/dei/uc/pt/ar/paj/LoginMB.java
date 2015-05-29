package dei.uc.pt.ar.paj;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dei.uc.pt.ar.paj.UserEntity;


/**
 *
 * @author 
 */
@Named("loginMB")
@RequestScoped
public class LoginMB implements Serializable{

    private static final long serialVersionUID = -6202006843421064331L;

    //The credentials to search for
    private String email;
    private String password;
    private String errorMessage;
    @EJB
	private UserFacade userFacade;
    @Inject
    private UserSession userSession;
    @EJB
    private PasswordEJB pw;
    @Inject
	ActiveSession session;
	
	@Inject
	VirtualEJB ejb;

    /**
     * Creates a new instance of LoginMB
     */
    public LoginMB() {
    }

    public PasswordEJB getPw() {
        return pw;
    }

    public void setPw(PasswordEJB pw) {
        this.pw = pw;
    }

    public UserSession getuserSession() {
        return userSession;
    }

    public void setuserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserFacade getuserFacade() {
        return userFacade;
    }

    public void setuserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
    
    public String searchUser() {
        userSession.setLoggedUser(userFacade.findByEmailPass(email, pw.encrypt(password)));
        if (userSession.getLoggedUser() != null) {
        	System.out.println("Logged_user= "+this.email);
        	doLogin(0);  
            return "index";
        } else {
            this.errorMessage = "Email/Password combination not found! Please try again";
            return "login";
        }
    }
    
    public String refreshUser() {
        userSession.setLoggedUser(userFacade.findByEmailPass(userSession.getLoggedUser().getEmail(), userSession.getLoggedUser().getPassword()));
        if (userSession.getLoggedUser() != null) {
        	      	
            return "index";
        } else {
            this.errorMessage = "Email/Password combination not found! Please try again";
            return "login";
        }
    }
    
    public void logged() throws IOException{
        if(userSession.getLoggedUser() != null){
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "index.xhtml");
        }
    }
    
    public void notLogged() throws IOException {
        if (userSession.getLoggedUser() == null) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
        }            
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml?faces-redirect=true";
    }
    
    public UserEntity getLoggedUser(){
        return userSession.getLoggedUser();
    }
    public void doLogin(int i){
		ejb.populate();
//		session.init(ejb.getUser(i));
		session.init(getLoggedUser());
		redirect();
	}
	
	private void redirect(){

		String redirect="index.xhtml";

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
		try {
			response.sendRedirect(redirect);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
}

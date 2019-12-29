package bioskop;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import bioskop.dao.ConnectionManager;

public class InitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0)  { 
    	// TODO Auto-generated method stub
    }

    public void contextInitialized(ServletContextEvent event)  {
    	System.out.println("inicijalizacija...");

    	ConnectionManager.open();

		System.out.println("zavrseno!");
    }

}

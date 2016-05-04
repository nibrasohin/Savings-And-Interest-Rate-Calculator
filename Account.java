package finalProject;

import java.util.Calendar;

public class Account 
{
	Calendar now = Calendar.getInstance();
	//global instance variables
	String userName;
	String acountName;
	
	String strtDate;
	int strtMonth;
	int strtYear;
	
	String endDate;
	int endMonth;
	int endYear;
	
	String availedDate;
	int availedMonth;
	int availedYear;
	
	int currMonth;
	int currYear;
	
	double interestRate;
	double primaryAmount;
	double finalAmount;
	double availed;
	
	int interestAdded;
	
	int totalDuration;
	
	public Account(String acntName,String userName,String start,String end,String availed,double r,double amnt,int intAdded)
	{
		this.interestAdded=intAdded;
		this.currMonth=(now.get(Calendar.MONTH) + 1);
		this.currYear=now.get(Calendar.YEAR);
		this.strtDate=start;
		this.endDate=end;
		this.availedDate=availed;
		this.setAcntName(acntName);
		this.setUserName(userName);
		this.setTime(start, 1);//sets the strt month and year
		this.setTime(end, 0);//sets the end month and year
		if(!availed.equals("empty"))
		{
			this.setTime(availed, 2);//sets the availed month and year
		}
		this.setInterest(r);
		this.setPrimaryAmnt(amnt);
		//Have to calculate to initialize values for 
		//totalDuration
		//availed
		//
	}
	
	
	public Account() {
		// TODO Auto-generated constructor stub
	}


	//sets time depending on whether to set the time for end or start date
	//if 1 set time for start date else if 0 for end date and 2 for availed date
	public void setTime(String date, int identifier)
	{
		String[] array=date.split("-");
		if(identifier==1 && array.length>=3)
		{
				this.strtMonth=Integer.parseInt(array[1]);
				this.strtYear=Integer.parseInt(array[2]);
			
		}
		else if(identifier==0 && array.length>=3)
		{
			this.endMonth=Integer.parseInt(array[1]);
			this.endYear=Integer.parseInt(array[2]);
		}
		else if (identifier==2 && array.length>=3)
		{
			this.availedMonth=Integer.parseInt(array[1]);
			this.availedYear=Integer.parseInt(array[2]);
		}
		else if(date.equals("empty"))
		{
			
		}
		else
		{
			System.out.println("Error Fix it!!");
		}
	}
	
	

	
	
	//setters
	public void setAcntName(String name)
	{
		this.acountName=name;
	}
	public void setUserName(String name)
	{
		this.userName=name;
	}
	public void setInterest(double r)
	{
		this.interestRate=r;
	}
	
	public void setAvailed(double avld)
	{
		this.availed=avld;
	}
	public void setFinalAmnt(double amnt)
	{
		this.finalAmount=amnt;
	}
	public void setPrimaryAmnt(double amnt)
	{
		this.primaryAmount=amnt;
	}
	public void setTotalDuration(int dur)
	{
		this.totalDuration=dur;
	}
}

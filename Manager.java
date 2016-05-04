package finalProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager
{
//	static double totalInterestAdded=0;
	static double totalFinalAmount=0;
	static double totalPrimaryAmount=0;
	static double totalInterestGained=0;
	static double toalMonthlyInterestRate=0;
	
	public Manager()
	{
		
	}
	
	//reads in the values from the user and initializes them
	public void initialize()
	{
		double monthlyInterestRate=0;
		int accountNo=1;
		Account account;
		String acntName="";
		String userName="";
		String start="";
		String end="";
		String availed="";
		double r=0.00;
		double amnt=0.00;
		int option=0;
		int interestAdded=0;
		double finalAmnt=0.00;
		//		double interestRate;
		try 
		{
			BufferedReader reader=new BufferedReader(new FileReader("info.txt"));
			PrintWriter writer=new PrintWriter(new BufferedWriter(new FileWriter("Summary.txt")));

			Scanner input=new Scanner(System.in);
			boolean loopBreaker=true;
			while(loopBreaker)
			{
				System.out.println("Enter 1 to see updated list:");				

				option=input.nextInt();
				if(option==1)
				{
					writer.write(String.format("%25s   %40s   %20s   %20s   %20s   %20s\n","Account Holder's Name","Account Name","Primary Amount(Tk)","Interest Added(Tk)","Final Amount(Tk)","Monthly Interest(Tk)"));
					writer.write(generateDivider(160, "-")+"\n");
					String line="";
					while(line!=null)
					{
						while((line=reader.readLine())!=null && !line.equals("end"))
						{
							String[] array=line.split("\\s+");
							if(array[0].equals("User:"))
							{
								userName=array[1];
							}
							else if(array[0].equals("Account_NO:"))
							{
								accountNo=Integer.parseInt(array[1]);
							}
							else if(array[0].equals("Account_Name:"))
							{
								acntName=array[1];
							}
							else if(array[0].equals("Starting_Date:"))
							{
								start=array[1];
							}
							else if(array[0].equals("End_Date:"))
							{
								end=array[1];
							}
							else if(array[0].equals("Availed:"))
							{
								availed=array[1];
							}
							else if(array[0].equals("Interest_Rate:"))
							{
								r=Double.parseDouble(array[1]);
							}
							else if(array[0].equals("Amount:"))
							{
								amnt=Double.parseDouble(array[1]);
							}
							else if(array[0].equals("Interest_Added:"))
							{
								interestAdded=Integer.parseInt(array[1]);
							}
							else
							{

							}	
						}//2nd while loop ends
						account=new Account(acntName,userName,start,end,availed,r,amnt,interestAdded);
						account.setTotalDuration(durationCalculator(account,account.currMonth,account.currYear,account.strtMonth,account.strtYear));
						if(availed.equals("empty"))
						{
							if(interestAdded==0)
							{
								finalAmnt=calcFinalAmmount(account, amnt, calcInterestRate(account, r, amnt));
							}
							else
							{
								finalAmnt=calcWithAddedInterest(account,interestAdded,account.primaryAmount,account.interestRate);
							}
							account.setFinalAmnt(finalAmnt);
						}
						else
						{
							if(interestAdded==0)
							{
								finalAmnt=calcFinalAmmount(account, amnt, calcInterestRate(account, r, amnt))-calcAvailed(account, durationCalculator(account,account.availedMonth,account.availedYear,account.strtMonth,account.strtYear), r, amnt);
							}
							else
							{
								finalAmnt=calcWithAddedInterest(account,interestAdded,amnt,r)-calcAvailed(account, durationCalculator(account,account.availedMonth,account.availedYear,account.strtMonth,account.strtYear), r, amnt);
							}
							account.setFinalAmnt(finalAmnt);
						}
						if(!userName.equals(""))
						{
							writer.write(String.format("%25s", "User: "+userName+"\n"));
							userName="";
						}
						monthlyInterestRate=calcMonthlyInterestRate(account,account.interestRate,account.primaryAmount);
						toalMonthlyInterestRate+=monthlyInterestRate;
						totalPrimaryAmount+=account.primaryAmount;
						totalFinalAmount+=account.finalAmount;
						totalInterestGained+=(account.finalAmount-account.primaryAmount);
						writer.write(String.format("%25s   %40s | %20.2f | %20.2f | %20.2f | %20f\n",accountNo++,account.acountName,account.primaryAmount,(account.finalAmount-account.primaryAmount),account.finalAmount,monthlyInterestRate));
						
					}//first while loop ends
					writer.write(generateDivider(160, "=")+"\n");
					writer.write(String.format("%25s %65.2f %23.2f %21.2f %22f\n","Total",totalPrimaryAmount,totalInterestGained,totalFinalAmount,toalMonthlyInterestRate));
					writer.close();					
					reader.close();
				}
				
				System.out.println(" To continue viewing the list or adding account enter 1 or enter 0 to Quit:");
				option=input.nextInt();
				loopBreaker=option==1;			
				input.close();
			}//loopbreaker while loop ends
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		} 

		System.out.println("Task Completed\nThanks for using this service!");
	}
	
	//calculates the duratioin on how long the money has been in the accounts for
	public int durationCalculator(Account a,int currMonth,int currYear,int primaryMonth,int primaryYear)
	{
		int result=0;
		if(a.endYear<a.currYear)
		{
			result=(12-primaryMonth)+a.endMonth+(a.endYear-primaryYear-1)*12;
		}
		else if(currYear>=primaryYear || a.endYear>=currYear)
		{
			result=(12-primaryMonth)+currMonth+(currYear-primaryYear-1)*12;
		}
		else if(currYear-primaryYear==1)
		{
			result=(12-primaryMonth)+currMonth;
		}
		return result;
	}
	
	//calculates the interest rate on a given amount
	public double calcInterestRate(Account a,double r,double amnt)
	{
		double result=((r/100.)/12)*amnt*a.totalDuration;
		return result;
	}
	public double calcMonthlyInterestRate(Account a,double r,double amnt)
	{
		return ((r/100.)/12)*amnt;
	}
	
	//returns the availed money from the account
	public double calcAvailed(Account a,int months,double r,double amnt)
	{
		double result=((r/100.)/12)*amnt*months;
		return result;
	}
	
	//Returns the final ammount after adding the interest rate to the primary account
	public double calcFinalAmmount(Account a,double primaryAmnt,double interestAdded)
	{
		return primaryAmnt+interestAdded ;	
	}
	
	//calculate the final ammount after adding the interest rate to the primary account and getting interest on that
	//will make use of methods to calculate intrest rate and to calculate the final amount
	//returns the final ammount including the interest rate
	public double calcWithAddedInterest(Account a,int interval,double amnt,double r)
	{
		double primaryAmnt=amnt;
		double finalAmnt=primaryAmnt;
		double interest;
		for (int i=0;i<=a.totalDuration;i+=interval)
		{
			interest=((r/100.)/12)*finalAmnt*interval;
			finalAmnt+=interest;
		}
		return finalAmnt;
	}
	
	//Prints the whole summarry in the console
	public void printSummary(ArrayList<String> u1, ArrayList<String> u2)
	{	
		for(int i=0;i<u1.size();i++)
		{
			System.out.println(u1.get(i));
		}
		for(int j=0;j<u2.size();j++)
		{
			System.out.println(u2.get(j));
		}		
	}
	
	public String generateDivider(int dividerTimes,String div)
	{
		String result="";
		for (int i=0;i<dividerTimes;i++)
		{
			result+=div;
		}
		return result;
	}
	public void updateList()
	{
		
	}
}//end of class

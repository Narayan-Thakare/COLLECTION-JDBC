import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
class Student{
	private int roll;
	private String name;
	private String adress;
	
	Student(int roll,String name,String adress)
	{
		this.roll=roll;
		this.name=name;
		this.adress=adress;
	}
	public int getRoll()  
	{	
		return roll;
	}
	public String getName()
	{	
		return name;
	}
	public String getAdress()
	{
	return adress;
	}
	public String toString()
	{
	return roll+" "+name+" "+adress;
	}
}
public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		List<Student> c = new ArrayList<Student>();
		Scanner s = new Scanner (System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
  		
  		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB1","root","abc123");
  	

		int choice=0;
		do{
			System.out.println("1.Add Student ");
			System.out.println("2.Show Student ");
			System.out.println("3.Search Student ");
			System.out.println("4.Delete  Student ");
			System.out.println("5.Update Student ");
			System.out.println("Enter the Choice");
			choice=Integer.parseInt(s.nextLine());
			
			switch (choice)
			{
				case 1:
				System.out.println("Enter the no of student");
				int no=Integer.parseInt(s.nextLine());
				for(int i=1;i<=no;i++)
				{
					System.out.println(i+" :Enter the roll no");
					int	roll=Integer.parseInt(s.nextLine());
					System.out.println("Enter the name");
					String name=s.nextLine();
					System.out.println("Enter the adress");
					String adress=s.nextLine();
					c.add(new Student(roll,name,adress));
					
					PreparedStatement stmt = con.prepareStatement("INSERT INTO StudentDB1.Stud VALUES(?,?,?)");
					
					stmt.setInt(1, roll);
					stmt.setString(2, name);
					stmt.setString(3, adress);
					int a = stmt.executeUpdate();
					if(a>0)
					{
						System.out.println("data inserted successfully!\n\n");
					}
					else
					{
						System.out.println("data is not inserted :(");
					}
				}
				break;
				
				case 2:
					Iterator<Student>i=c.iterator();
					System.out.println("~ Show using ArrayList ~");
					while(i.hasNext())
					{
						Student st=i.next();
						System.out.println(st);
					}
					
					ResultSet rs=null;
					
					PreparedStatement stmt = con.prepareStatement("SELECT * FROM StudentDB1.Stud");
					
					
					rs = stmt.executeQuery();
					System.out.println("\n~~ Show from Database ~~");
					while(rs.next()) {
						System.out.println(rs.getInt(1)+ " "+rs.getString(2)+" "+rs.getString(3));
					}
					break;
			
				case 3:
					boolean found=false;
					System.out.println("Enter he roll no");
					int	roll=Integer.parseInt(s.nextLine());
					i=c .iterator();
					while(i.hasNext())
					{
						Student st=i.next();
						if(st.getRoll() == roll)
					{
							System.out.println(st);
							found =true;
						}
					}
					
					stmt = con.prepareStatement("SELECT * FROM StudentDB1.Stud WHERE roll=?");
					System.out.println("\nStatement created");
					stmt.setInt(1, roll);
					
					rs=null;
					rs = stmt.executeQuery();
					
					
					while(rs.next()) {
						System.out.println(rs.getInt(1)+ " "+rs.getString(2)+" "+rs.getString(3));
						found=true;
					}
					if(!found)
					{
						System.out.println("Student is not found");
					}
					break;
				
				case 4:
					found=false;
					System.out.println("Enter the roll no");
					roll=Integer.parseInt(s.nextLine());
					i=c.iterator();
					while(i.hasNext())
					{
						Student st=i.next();
						if(st.getRoll()==roll)
						{
							i.remove();
							found =true;
						}
					}
					
					stmt = con.prepareStatement("DELETE FROM StudentDB1.Stud WHERE roll = ?");
					System.out.println("\nStatement created");
					stmt.setInt(1, roll);
					
					int a = stmt.executeUpdate();
					if(a>0)
					{
						System.out.println("data deleted successfully from database!");
					}
					else
					{
						System.out.println("data is not deleted from database:");
					}
					
					if(!found)
					{
						System.out.println("Student is not found in ArrayList");
					}  
					else
					{
						System.out.println("Student deleted successfully from ArrayList");
					}
					break;
			
				case 5:
					found=false;
					String name1=null, adress1=null;
					System.out.println("Enter the roll no");
					roll=Integer.parseInt(s.nextLine());
					ListIterator<Student>li=c.listIterator();
					
					while(li.hasNext())
					{
						Student st=li.next();
						if(st.getRoll()==roll)
						{
							System.out.println("Enter the name");
							name1=s.nextLine();
							System.out.println("Enter the adress");
							adress1=s.nextLine();
							li.set(new Student(roll,name1,adress1));
							found =true;
						}
					}
					stmt = con.prepareStatement("UPDATE StudentDB1.Stud SET name=?, adress=? WHERE roll = ?");
					System.out.println("\nStatement created");
					stmt.setString(1, name1);
					stmt.setString(2, adress1);
					stmt.setInt(3, roll);
					a = stmt.executeUpdate();
					if(a>0)
					{
						System.out.println("Student updated succecssfully in Database!");
					}
					else
					{
						System.out.println("Student is not found in Database :(");
					}
					if(!found){
						System.out.println("Student is not found in ArrayList");
					}
					else{	
						System.out.println("Student updated succecssfully in ArrayList");
					}
					break;	
			
			}		
		}
		while(choice!=0);	
		
	}

}

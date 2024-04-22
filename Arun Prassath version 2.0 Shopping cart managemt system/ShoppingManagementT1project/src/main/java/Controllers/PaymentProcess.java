package Controllers;
import java.sql.SQLException;
import java.util.Scanner;

import Controllers.CustomerProduct.CartInfo;
import DBConnections.DBConnectProductTable;
import DBConnections.DBConnectTable;

public class PaymentProcess implements UpimodePayment,DebitcardmodePayment {
	DBConnectTable connectTable=new DBConnectTable();
	//PaymentProcess process =new PaymentProcess();
	Scanner input22=new Scanner(System.in);

	public void proceedToPayment()
	{
		try {
		//DBConnectProductTable payConnect = new DBConnectProductTable();
       // payConnect.ConnectDB();
		
		System.out.println("Enter the PaymentID : (Ex..B1001)  : ");
		String PaymentID=input22.nextLine();
		input22.nextLine();
		System.out.println("Enter the cartID (Ex.. D1001): ");
		String PcartID=input22.nextLine();
		String Table_PP="cart";
		String primary_pp="cartid";
		String record_PP="ProductID";
		connectTable.ConnectDB();
		
		String PProductId=connectTable.selectrecordDisplayByID(Table_PP,primary_pp,PcartID,record_PP);
		System.out.println(PProductId);
		
		String PuserID=connectTable.selectrecordDisplayByID(Table_PP,primary_pp,PcartID,"userID");
		String PBillAmount=connectTable.selectrecordDisplayByID(Table_PP,primary_pp,PcartID,"totalcost");
		System.out.println(PuserID+"--"+PBillAmount);
		String Pmode = null;
		String paymentstatus=null;
		System.out.println("Are you conform to process the payment (yes[y]/no[n]) : ");
		String conformtopayment=input22.next();
		if(conformtopayment.equalsIgnoreCase("y")) {
			System.out.println("Select the payment method  :");
			System.out.println("1.UPI");
			System.out.println("2.Debit card ");
			System.out.println("Enter your choice : ");
			int choice=input22.nextInt();
			input22.nextLine();
			System.out.println("Amount to pay : Rs."+PBillAmount );
			switch(choice) {
			case 1:
				paymentstatus=DoUPIPayment();
				Pmode="UPI";
				break;
			case 2:
				
				paymentstatus=DoProcessDebitcard();
				Pmode="Debitcard";
				
				break;
			default:
				System.out.println("Invalid input");
		
			}
			
			

	        String sql = "INSERT INTO Payment (PaymentId, cartid,productid ,userid, totalcost,statuspayment,pmode) VALUES (?, ?, ?, ?, ?, ?,?)";
	        //Object paymentstatus;
			Object[] params = {PaymentID,PcartID, PProductId, PuserID, PBillAmount, paymentstatus,Pmode};

	        int rowsInserted = connectTable.InsertDB(sql, params);
	        if (rowsInserted > 0)
	            System.out.println("Payment  was recorded successfully!");
	        else
	            System.out.println("Payment is failed to record ");
			
		}	
		
		if(paymentstatus.equalsIgnoreCase("paid")) {
			connectTable.DeleteProfileProduct("cart","cartid","cart product",PcartID);
		}
		// TODO Auto-generated methd stub
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	 private String validate1Id(Scanner scanner) {
	        String userId;
	        boolean validUserId;

	        do {
	        	scanner.nextLine();
	            System.out.print("Enter the USER ID [A1001- Admin/ C1001--Customer]: ");
	            userId = scanner.nextLine();
	            validUserId = isValidUserId(userId);

	            if (!validUserId) {
	                System.out.println("Invalid ID: ID must contain only alphanumeric characters.");
	            }
	        } while (!validUserId);

	        return userId;
	    }

	    private boolean isValidUserId(String userId) {
	        return userId.matches("^[a-zA-Z0-9]+$");
	    }

	    

	public void ViewPaymentstatus() {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter your Payment ID (Ex..B1001) : ");
		String CheckPaymentId=validate1Id(scanner);
		connectTable.ConnectDB();
		String statusretrieve=connectTable.selectrecordDisplayByID("payment","paymentID",CheckPaymentId,"STATUSPAYMENT");
		System.out.println("Status of payment is "+statusretrieve);
		// TODO Auto-generated method stub
		
	}

	@Override
	public String DoUPIPayment() {
		
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter your UPI id :");
		String upiid=validate1Id(scanner);

		
		System.out.println("option --1.pay /2.Not Pay : ");
		int conformprocess=input22.nextInt();
		String paymentstatusAs;
		//String paymentstatus;
		if(conformprocess==1) {
			System.out.println("Payment sucessfully ! products will be reach as soon as possible ");
			paymentstatusAs="paid";
			
		}else {
			System.out.println("Sorry , Your Payment failed ! try again Later");
			paymentstatusAs="not paid";
		}
		return paymentstatusAs;
		// TODO Auto-generated method stub
		
	}
	
	private String validateDebitCardNumber(Scanner scanner) {
	    String debitcard;
	    boolean validDebitCard;

	    do {
	        System.out.print("Enter your Debit card number: ");
	        debitcard = scanner.nextLine();
	        validDebitCard = isValidDebitCardNumber(debitcard);

	        if (!validDebitCard) {
	            System.out.println("Invalid debit card number. Please try again.");
	        }
	    } while (!validDebitCard);

	    return debitcard;
	}
	private boolean isValidDebitCardNumber(String debitcard) {
	    // Add your debit card number validation logic here
	    // For example, you can check the length and ensure it contains only digits
	    return debitcard.matches("^[0-9]{16}$");
	}

	@Override
	public String DoProcessDebitcard() {
		System.out.println("Enter your Debit card number :");
		String debitcard= validateDebitCardNumber(input22);
		
		System.out.println("Enter Debit card pin  : ");
		String pindebitcard=validateDebitCardPin(input22);

		System.out.println("option --1.pay /2.Not Pay : ");
		int conformprocess2=input22.nextInt();
		String paymentstatusAs;
		//String paymentstatus;
		if(conformprocess2==1) {
			System.out.println("Payment sucessfully ! products will be reach as soon as possible ");
			paymentstatusAs="paid";
			
		}else {
			System.out.println("Sorry , Your Payment failed ! try again Later");
			paymentstatusAs="notpaid";
		}
		return paymentstatusAs;
		// TODO Auto-generated method stub
		//return null;
	}

	
	private String validateDebitCardPin(Scanner scanner) {
	    String pindebitcard;
	    boolean validPin;

	    do {
	        System.out.print("Enter Debit card pin: ");
	        pindebitcard = scanner.nextLine();
	        validPin = isValidDebitCardPin(pindebitcard);

	        if (!validPin) {
	            System.out.println("Invalid debit card pin. Please try again.");
	        }
	    } while (!validPin);

	    return pindebitcard;
	}

	private boolean isValidDebitCardPin(String pindebitcard) {
	    // Add your debit card PIN validation logic here
	    // For example, you can check the length and ensure it contains only digits
	    return pindebitcard.matches("^[0-9]{4}$");
	}
}

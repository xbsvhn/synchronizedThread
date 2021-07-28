package synchronizedThread;

public class Customer{
    private int taiKhoan = 10000;

    public Customer() {
        System.out.println("Tài khoản hiện có = " + taiKhoan);
    }

    private synchronized void rutTien(int soTienRut) {
        System.out.println("Giao dịch rút tiền đang được thực hiện với" +
                " số tiền = " + soTienRut + "...");

        if (taiKhoan < soTienRut) {
            System.out.println("Số tiền trong tài khoản không đủ!");
            try {
                wait(); // phương thức wail sẽ đưa Thread rơi vào trạng thái sleeping
            } catch (InterruptedException ie) {
                System.out.println(ie.toString());
            }
        }

        taiKhoan -= soTienRut;
        System.out.println("Rút tiền thành công. Số tiền hiện có trong tài khoản = " + taiKhoan);
    }

    private synchronized void nopTien(int soTienNop) {
        System.out.println("Giao dịch nạp tiền đang được thực hiện với" +
                " số tiền nạp = " + soTienNop + "...");
        taiKhoan += soTienNop;
        System.out.println("Nạp tiền thành công. Số tiền hiện có trong tài khoản = " + taiKhoan);
        notify();
    }
    public static void main(String[] args) {

        final Customer customer = new Customer();

        Thread t1 = new Thread(){
            public void run() {
                customer.rutTien(20000);
            }

        };

        t1.start();

        Thread t2 = new Thread(){

            public void run() {
                customer.nopTien(30000);
            }
        };

        t2.start();

    }

}

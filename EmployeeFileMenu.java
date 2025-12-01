import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileEmployee implements Serializable {
    private static final long serialVersionUID = 1L;
    int id; String name; String designation; double salary;
    public FileEmployee(int id, String name, String designation, double salary) {
        this.id=id; this.name=name; this.designation=designation; this.salary=salary;
    }
    @Override public String toString() {
        return String.format("FileEmployee[id=%d , name=%s , designation=%s , salary=%.2f]", id , name , designation , salary);
    }
}

public class EmployeeFileMenu {
    private static final String FILE = "employees.dat";

    private static List<FileEmployee> readAll() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (List<FileEmployee>) obj;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static void writeAll(List<FileEmployee> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.Add Employee 2.Display All 3.Exit");
            String c = sc.nextLine();
            if ("1".equals(c)) {
                System.out.print("Name: "); String name = sc.nextLine();
                System.out.print("Id: "); int id = Integer.parseInt(sc.nextLine());
                System.out.print("Designation: "); String desig = sc.nextLine();
                System.out.print("Salary: "); double sal = Double.parseDouble(sc.nextLine());
                List<FileEmployee> list = readAll();
                list.add(new FileEmployee(id, name, desig, sal));
                writeAll(list);
                System.out.println("Saved.");
            } else if ("2".equals(c)) {
                List<FileEmployee> list = readAll();
                if (list.isEmpty()) System.out.println("No employees saved.");
                else list.forEach(System.out::println);
            } else if ("3".equals(c)) {
                System.out.println("Exit"); sc.close(); return;
            } else System.out.println("Invalid.");
        }
    }
}

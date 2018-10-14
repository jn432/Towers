package towers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

class Disk {
    private int diskNum;
    private Rod rod;
    
    public Disk(int diskNum, Rod rod) {
        this.diskNum = diskNum;
        this.rod = rod;
    }
    
    public int getDiskNum() {
        return diskNum;
    }
    
    public int getRodNum() {
        return rod.getRodNum();
    }
    
    public void move(Rod newRod) {
        if (rod.get(rod.size()-1) != this) {
            System.err.println("this disk is not on the top of the rod");
        }
        else if (newRod.isEmpty()) {
            rod.remove(this);
            newRod.add(this);
            rod = newRod;
            System.out.println("Disk " + diskNum + " moves to Rod " + newRod.getRodNum());
        }
        else if ((this.diskNum > newRod.get(newRod.size()-1).diskNum) ) {
            System.err.println("target rod has a smaller disk on top");
        }
        else {
            rod.remove(this);
            newRod.add(this);
            rod = newRod;
            System.out.println("Disk " + diskNum + " moves to Rod " + newRod.getRodNum());
        }
    }
}

class Rod extends ArrayList<Disk> {
    
    private int rodNum;
    
    public Rod(int rodNum) {
        super();
        this.rodNum = rodNum;
    }
    
    public int getRodNum() {
        return rodNum;
    }
}

public class Towers {
    
    private static boolean hanoiAlgorithm(ArrayList<Disk> disks, Rod[] rods) {
        if (disks.isEmpty()) {
            return true;
        }
        else {
            ArrayList<Disk> disksRecursion = new ArrayList<>();
            disksRecursion.addAll(disks);
            disksRecursion.remove(0);
            //System.out.println("disks total after removing: " + disksRecursion.size());
            hanoiAlgorithm(disksRecursion, rods);
            
            Disk d = disks.get(0);
            if ((d.getDiskNum() % 2) == 1) {
                d.move(rods[((d.getRodNum() + 1) % 3)]);
            }
            else {
                d.move(rods[((d.getRodNum() + 2) % 3)]);
            }
            hanoiAlgorithm(disksRecursion, rods);
            return false;
        }
    }

    public static void main(String[] args) {
        //create scanner
        Scanner input = new Scanner(System.in);
        //create 3 rods
        Rod[] rods = {new Rod(0), new Rod(1), new Rod(2)};
        
        int diskTotal = 0;
        while (diskTotal == 0) {
            try {
                System.out.print("Enter number of disks to solve(int > 0): ");
                diskTotal = input.nextInt();
                if (diskTotal <= 0) {
                    System.out.println("integer must be greater than 0");
                    diskTotal = 0;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Try following the instructions...");
                input.nextLine();
            }
        }
        
        ArrayList<Disk> disks = new ArrayList<>();
        for (int i = diskTotal; i >= 1; i--) {
            Disk d = new Disk(i, rods[0]);
            rods[0].add(d);
            disks.add(d);
        }
        hanoiAlgorithm(disks, rods);
        System.out.println("End of algorithm");
    }
    
}

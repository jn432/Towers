package towers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//Disks to move around rods, keeps track of where each disk is, 1 is smallest disk
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
        //check if this disk is on top of the rod
        if (rod.get(rod.size()-1) != this) {
            System.err.println("this disk is not on the top of the rod");
        }
        else {
            //check if target rod is empty, move disk to that rod if true
            if (newRod.isEmpty()) {
                rod.remove(this);
                newRod.add(this);
                rod = newRod;
                System.out.println("Disk " + diskNum + " moves to Rod " + newRod.getRodNum());
            }
            //rod is not empty, check if this disk is larget than the top disk of target rod, no move, give error message
            else if ((this.diskNum > newRod.get(newRod.size()-1).diskNum) ) {
                System.err.println("target rod has a smaller disk on top");
            }
            //rod is not empty and this disk is smaller than the top disk of target rod, move
            else {
                rod.remove(this);
                newRod.add(this);
                rod = newRod;
                System.out.println("Disk " + diskNum + " moves to Rod " + newRod.getRodNum());
            }
        }
    }
}

//mainly for keeping track of which rod is which
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
    
    //recursion statement
    private static void hanoiAlgorithm(ArrayList<Disk> disks, Rod[] rods) {
        //trivial case, no more disks to move
        if (disks.isEmpty()) {
        }
        else {
            //create an array of disks with the largest one missing(n - 1)
            ArrayList<Disk> disksRecursion = new ArrayList<>();
            disksRecursion.addAll(disks);
            disksRecursion.remove(0);
            
            //System.out.println("disks total after removing: " + disksRecursion.size());
            
            //move all disks except largest out of the way
            hanoiAlgorithm(disksRecursion, rods);
            
            //get the largest disk and move it
            Disk d = disks.get(0);
            if ((d.getDiskNum() % 2) == 1) {
                d.move(rods[((d.getRodNum() + 1) % 3)]);
            }
            else {
                d.move(rods[((d.getRodNum() + 2) % 3)]);
            }
            
            //move the disks on top of the largest disk
            hanoiAlgorithm(disksRecursion, rods);
        }
    }

    public static void main(String[] args) {
        //create scanner
        Scanner input = new Scanner(System.in);
        //create 3 rods
        Rod[] rods = {new Rod(0), new Rod(1), new Rod(2)};
        
        //ask for the number of disks
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
        
        //create the list and add it onto the first rod, and the arraylist of disks
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

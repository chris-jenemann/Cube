/*I found a thread where people talked about taking an approach to creating a cube using this somw what optimized format
storing just the location and orientation each peice, and I think i was able to figure out how to create the moves, but i
have no clue how to manage to display the cube in a meaningful way
https://stackoverflow.com/questions/500221/how-would-you-represent-a-rubiks-cube-in-code*/ 
public class State {
    static int[] edges =new int[12];
    static int[] edgeO = new int[12];

    static int[] corners= new int[8];
    static int[] cornersO= new int[8];
//just sets every thing to the solved state, so each orineation is 0 and peices are ordered 
public void setStart()
    {
        for(int i=0;i<8;i++)
        {
            corners[i]=i;
            cornersO[i]=0;
        }

        for(int i=0;i<12;i++)
        {
            edges[i]=i;
            edgeO[i]=0;
        }
    }
//helper function to switch 4 values in an array
public void switcher(int[] arr,int a, int b , int c, int d)
{
    int temp = arr[d];
    arr[d] = arr[c];
    arr[c] = arr[b];
    arr[b] = arr[a];
    arr[a] = temp;
}

//right move
public void r()
{   //simple just switch the peices of cubes position UFR ->UBR ->DBR ->DFR ->UFR
    switcher(corners,0,3,7,4);
    switcher(cornersO,0,3,7,4);
    // not sure if i have this down but imainge that each cornder is pyramid and the ones that stay in on there og face 
    //are only rotated 1 way and the ones that move to a different face are rotated the other way, so i just add 1 or 2 mod 3 to change the orientation

    cornersO[0] = (cornersO[0] + 2) % 3;
    cornersO[3] = (cornersO[3] + 1) % 3;
    cornersO[7] = (cornersO[7] + 2) % 3;
    cornersO[4] = (cornersO[4] + 1) % 3;
    //switch the edges in the same way UR->UB->DB->DF->UR
    switcher(edges,1,5,9,10);
    //no o change in edge orientation for R moves
}
public void rP()
    {   
        r();
        r();
        r();
    }
public void l()
{
    switcher(corners,1,5,6,2);
    switcher(cornersO,1,5,6,2);

    cornersO[1]=(cornersO[1]+1)%3; 
    cornersO[5]=(cornersO[5]+2)%3;
    cornersO[6]=(cornersO[6]+1)%3;  
    cornersO[2]=(cornersO[2]+2)%3;

    switcher(edges,0,4,8,11);

}

    public void lP()
    {
        l();
        l();
        l();
    }
    public void u()
    {
        //super easy nothing changes orientation and its just a 4 cycle of the top face pieces
        switcher(corners,0,1,2,3);
        switcher(edges, 0, 1, 2, 3);
        
    }
    public void uP()
    {
        u();
        u();
        u();
    }   
    public void d()
    {
        switcher(corners, 4,7,6,5);
        switcher(edges, 4, 5, 6, 7);

    }  
    public void dP()
    {
        d();
        d();
        d();
    }
    public void f()
{
    switcher(corners, 1,0, 4, 5);
    switcher(cornersO,1,0,4,5);

    switcher(edges, 0, 8, 4, 9);
    switcher(edgeO,0,8,4,9);

    edgeO[0]^=1;
    edgeO[8]^=1;
    edgeO[4]^=1;
    edgeO[9]^=1;

    cornersO[1]=(cornersO[1]+1)%3;
    cornersO[5]=(cornersO[5]+2)%3;
    cornersO[4]=(cornersO[4]+1)%3;
    cornersO[0]=(cornersO[0]+2)%3;
}

    public void fP()
    {
        f();
        f();
        f();
    }
    public void b()
{
    switcher(corners, 2,3, 7, 6);
    switcher(cornersO,2,3,7,6);

    switcher(edges, 2, 11, 6, 10);
    switcher(edgeO,2,11,6,10);

    edgeO[2]^=1;
    edgeO[11]^=1;
    edgeO[6]^=1;
    edgeO[10]^=1;

    cornersO[2]=(cornersO[2]+1)%3;
    cornersO[3]=(cornersO[3]+2)%3;
    cornersO[7]=(cornersO[7]+1)%3;
    cornersO[6]=(cornersO[6]+2)%3;
}

    public void bP()
    {
        b();
        b();
        b();
    }

//to model this i need to assign each peice two or three colors and then take the data from the array to determine which color is on each face

public  void showNums()
{
    
}


    public static void main(String[] args) {
        State cube = new State();
        cube.setStart(); // solved cube


    }

}

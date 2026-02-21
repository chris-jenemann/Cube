import java.util.Scanner;

public class App {
    static String[][] checkCube =   {
                                        {
                                            "w","b","w",
                                            "b","w","b",
                                            "w","b","w"
                                        },
                                        {
                                            "b","w","b",
                                            "w","b","w",
                                            "b","w","b"
                                        },
                                        {
                                            "r","g","r",
                                            "g","r","g",
                                            "r","g","r"
                                        },
                                        {
                                            "g","r","g",
                                            "r","g","r",
                                            "g","r","g"
                                        },
                                        {
                                            "y","o","y",
                                            "o","y","o",
                                            "y","o","y"
                                        },
                                        {
                                            "o","y","o",
                                            "y","o","y",
                                            "o","y","o"
                                        }, 
                                    };
    static String[][] cube =   {
                                    {
                                        "w","w","w",
                                        "w","w","w",
                                        "w","w","w"
                                    },
                                    {
                                        "b","b","b",
                                        "b","b","b",
                                        "b","b","b"
                                    },
                                    {
                                        "r","r","r",
                                        "r","r","r",
                                        "r","r","r"
                                    },
                                    {
                                        "g","g","g",
                                        "g","g","g",
                                        "g","g","g"
                                    },
                                    {
                                        "y","y","y",
                                        "y","y","y",
                                        "y","y","y"
                                    },
                                    {
                                        "o","o","o",
                                        "o","o","o",
                                        "o","o","o"
                                    }, 
                                };
    public static void main(String[] args) throws Exception {
       
        RubiksCube Cube = new RubiksCube();

        Scanner scn = new Scanner(System.in);
        Cube myCube=new Cube();
        myCube.l();
     
        String[][][] temp =myCube.remapForDisplay(myCube.getCube().clone());
        

        System.out.println("Would you like to see the solved cube (1) or checkered cube (2)? (1,2) ");
       

            myCube.printCube();
            Cube.show(temp);
        
            //Cube.show(checkCube);

        scn.close();

    }
}


 
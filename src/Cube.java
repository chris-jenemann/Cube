import java.util.ArrayList;
import java.util.Scanner;

public class Cube {
  //make this an array list so we can store lots of moves without worrying about size
  ArrayList < String > reverseMoves = new ArrayList < >();

  //3d array to represent the cube, each face is a 3x3 array of colors
  //face indices: 0=Up(white), 1=Back(blue), 2=Right(red), 3=Front(green), 4=Left(orange), 5=Down(yellow)
  //Im not sure if this will work with the auto grader, but I did make the format in the print match the canvas
  //https://onlinecube.com/ 
     String[][][] cube = {
        {{"w","w","w"},
        {"w","w","w"},
        {"w","w","w"}},  

        {{"b","b","b"},
        {"b","b","b"},
        {"b","b","b"}}, 

        {{"r","r","r"},
        {"r","r","r"},
        {"r","r","r"}}, 

        {{"g","g","g"},
        {"g","g","g"},
        {"g","g","g"}}, 

        {{"o","o","o"}
        ,{"o","o","o"},
        {"o","o","o"}},

        {{"y","y","y"},
        {"y","y","y"},
        {"y","y","y"}}   
    };

  //prints the cube in a standard cube net for development purposes
  public void DevelopmentprintCube() {
    //print top
    for (int i = 0; i < 3; i++) {
      //formatting
      System.out.print("        ");
      for (int j = 0; j < 3; j++) {
        System.out.print(cube[0][i][j]);
      }
      System.out.println();
    }

    //print mid line
    //face order: Back, Right, Front, Left For readablity 
    int[] temp = {1,4,3,2};
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          System.out.print(cube[temp[j]][i][k]);
        }
        System.out.print(" ");
      }
      System.out.println();
    }

    //print bottom
    for (int i = 0; i < 3; i++) {
      System.out.print("        ");
      for (int j = 0; j < 3; j++) {
        System.out.print(cube[5][i][j]);
      }
      System.out.println();
    }
  }

  //rotates face clockwise
  //Keeps cube consistant with turns
  //thought i didn't need this but broke after multiple moves without it
  //will have to be used 3 times when a face is rotated counter clockwise like on d and l
  private void rotateFace(int face) {
    String[][] temp = new String[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        temp[j][2 - i] = cube[face][i][j];
      }
    }

    cube[face] = temp;
  }

//rotates left face, Black column comes forward
  public void l() {
    String[] temp = new String[3];

    // Save Up left column
    for (int i = 0; i < 3; i++) {
        temp[i] = cube[0][i][0];
    }

    for (int i = 0; i < 3; i++) {
        cube[0][i][0] = cube[1][2 - i][2]; // fixed: add inversion
    }

    // Down left column -> Back right column (inverted)
    for (int i = 0; i < 3; i++) {
        cube[1][2 - i][2] = cube[5][i][0]; // correct
    }

    // Front left column -> Down left column
    for (int i = 0; i < 3; i++) {
        cube[5][i][0] = cube[3][i][0]; // fixed: remove inversion
    }

    // Saved Up left column -> Front left column
    for (int i = 0; i < 3; i++) {
        cube[3][i][0] = temp[i]; // correct
    }

    rotateFace(4);
    rotateFace(4);
    rotateFace(4);
}
  public void lP() {
    l();
    l();
    l();
  }

//rotates blue face, Black column goes backwards
  public void r() {
    String[] temp = new String[3];

    // Save Up right column
    for (int i = 0; i < 3; i++) {
        temp[i] = cube[0][i][2];
    }

    // Front right column -> Up right column
    for (int i = 0; i < 3; i++) {
        cube[0][i][2] = cube[3][i][2]; // fixed: remove inversion
    }

    // Down right column -> Front right column
    for (int i = 0; i < 3; i++) {
        cube[3][i][2] = cube[5][i][2]; // fixed: remove inversion
    }

    // Back left column (inverted) -> Down right column
    for (int i = 0; i < 3; i++) {
        cube[5][i][2] = cube[1][i][0]; // correct
    }

    // Saved Up right column -> Back left column (inverted)
    for (int i = 0; i < 3; i++) {
        cube[1][2 - i][0] = temp[i]; // correct
    }

    rotateFace(2); // fixed: 1x CW, not 3x
}
  public void rP() {
    r();
    r();
    r();
  }

//up, red row comes to front
public void u() {
    String[] temp = cube[3][0].clone(); // save Front top row
    cube[3][0] = cube[4][0].clone(); // Left top  -> Front top
    cube[4][0] = cube[1][0].clone(); // Back top  -> Left top
    cube[1][0] = cube[2][0].clone(); // Right top -> Back top
    cube[2][0] = temp;               // saved Front top -> Right top
    rotateFace(0);
}
  public void uP() {
    u();
    u();
    u();
  }

////down, orange row comes to front
  public void d() {
    //cycled opposite to U because D is rotated opposite of U
  // Should be:
    String[] temp = cube[3][2].clone();
    cube[3][2] = cube[2][2].clone(); // Right -> Front
    cube[2][2] = cube[1][2].clone(); // Back  -> Right
    cube[1][2] = cube[4][2].clone(); // Left  -> Back
    cube[4][2] = temp;               // Front -> Left
    rotateFace(5);
    rotateFace(5);
    rotateFace(5);
    
  }
  public void dP() {
    d();
    d();
    d();
  }

//green face rotates
 public void f() {
    String[] upBottom = cube[0][2].clone();
    String[] downTop = cube[5][0].clone();
    String[] leftRight = {
        cube[4][0][2],
        cube[4][1][2],
        cube[4][2][2]
    };
    String[] rightLeft = {
        cube[2][0][0],
        cube[2][1][0],
        cube[2][2][0]
    };

    // Up bottom row -> Right left col (inverted)
    for (int i = 0; i < 3; i++) {
        cube[2][i][0] = upBottom[2-i]; // correct
    }

    // Left right col -> Up bottom row (inverted)
    for (int i = 0; i < 3; i++) {
        cube[0][2][i] = leftRight[2-i]; // fixed: add inversion
    }

    // Down top row -> Left right col (inverted)
    for (int i = 0; i < 3; i++) {
        cube[4][2-i][2] = downTop[i]; // correct
    }

    // Right left col -> Down top row (inverted)
    for (int i = 0; i < 3; i++) {
        cube[5][0][i] = rightLeft[2-i]; // fixed: add inversion
    }

    rotateFace(3); // assuming 3 is Front face index
}
  public void fP() {
    f();
    f();
    f();
  }

//blue face rotates
  public void b() {
    String[] upTop = cube[0][0].clone();
    String[] downBottom = cube[5][2].clone();
    String[] leftLeft = {
        cube[4][0][0],
        cube[4][1][0],
        cube[4][2][0]
    };
    String[] rightRight = {
        cube[2][0][2],
        cube[2][1][2],
        cube[2][2][2]
    };

    // Up top row -> Left left col (inverted)
    for (int i = 0; i < 3; i++) {
        cube[4][2-i][0] = upTop[i]; // fixed: add inversion
    }

    // Right right col -> Up top row (inverted)
    for (int i = 0; i < 3; i++) {
        cube[0][0][i] = rightRight[2-i]; // correct
    }
      // Down bottom row -> Right right col (inverted)
    for (int i = 0; i < 3; i++) {
        cube[2][2-i][2] = downBottom[i]; // fixed
    }

    // Left left col -> Down bottom row (inverted)
    for (int i = 0; i < 3; i++) {
        cube[5][2][i] = leftLeft[2-i]; // correct
    }

    rotateFace(1);
    rotateFace(1);
    rotateFace(1);
}
  public void bP() {
    b();
    b();
    b();
  }

//prints all faces of the cube for auto grader
  public void printCube() {
    int[] faceOrder = {2,1,4,3,5,0}; // Up, Back, Right, Front, Left, Down
    for (int face: faceOrder) {
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          System.out.print(cube[face][i][j]);
        }
        System.out.println();
      }
      System.out.println();
    }
    System.out.println();
  }

//parses input string into the corresponding move
  public void mover(String move) {
    switch (move) {
    case "R":
      reverseMoves.add("R'");
      r();
      break;
    case "R'":
      reverseMoves.add("R");
      rP();
      break;
    case "L":
      reverseMoves.add("L'");
      l();
      break;
    case "L'":
      reverseMoves.add("L");
      lP();
      break;
    case "U":
      reverseMoves.add("U'");
      u();
      break;
    case "U'":
      reverseMoves.add("U");
      uP();
      break;
    case "D":
      reverseMoves.add("D'");
      d();
      break;
    case "D'":
      reverseMoves.add("D");
      dP();
      break;
    case "F":
      reverseMoves.add("F'");
      f();
      break;
    case "F'":
      reverseMoves.add("F");
      fP();
      break;
    case "B":
      reverseMoves.add("B'");
      b();
      break;
    case "B'":
      reverseMoves.add("B");
      bP();
      break;
    case "exit":
      break;
    default:
      System.out.println("invalid move");
    }
  }

  //retruns cube for 3d 
  public String[][][] getCube() {
    return cube;
  }

//scramble generator, applies a random number of random moves
  public void scramble() {
    String[] moves = {"R","R'","L","L'","U","U'","D","D'","F","F'","B","B'"};
    //from 15 to 35 moves to scramble
    //any cube is solvable in 20 moves but we go higher to make it more challenging
    int num = (int)(Math.random() * 20 + 15);
    System.out.println("Scrambling with " + num + " moves");
    for (int i = 0; i < num; i++) {
      int random = (int)(Math.random() * 12);
      mover(moves[random]);
    }
  }

//print reverse of moves 
  public void printSolve(ArrayList < String > moves) {
    for (String move: moves) {
      System.out.print(move + " ");
    }
  }
 
  public String[][][] remapForDisplay(String[][][] src) {
    String[][][] temp = new String[6][3][3];

    for (int r = 0; r < 3; r++)
    for (int c = 0; c < 3; c++) {
      temp[0][r][c] = src[3][r][c]; // Front
      temp[1][r][c] = src[1][r][c]; // Back
      temp[2][r][c] = src[2][r][c]; // Right
      temp[3][r][c] = src[4][r][c]; // Left
      temp[4][r][c] = src[0][r][c]; // Top
      temp[5][r][c] = src[5][r][c]; // Bottom
    }

    return temp;
  }

  public static void main(String[] args) throws Exception {
    Cube cube = new Cube();
    RubiksCube d = new RubiksCube();
    d.setCubeColors(cube.remapForDisplay(cube.getCube()));

    Scanner scn = new Scanner(System. in );
    System.out.print("Please input mode (Test/Normal/Scramble): ");
    String mode = scn.nextLine();

    if (mode.equals("Test")) {
      while (true) {
        System.out.println("Input Move (R, R', L, L', etc.)");
        String move = scn.nextLine();
        cube.mover(move);
        cube.DevelopmentprintCube();
        if (move.equals("exit")) {

          System.exit(0);
        }
      }
    }
    else if (mode.equals("Normal")) {
      while (true) {
        System.out.println("Input Move (R, R', L, L', etc.)");
        String move = scn.nextLine();
        cube.mover(move);
        cube.DevelopmentprintCube();
        if (move.equals("exit")) {
          cube.printSolve(cube.reverseMoves);
          System.exit(0);
        }
      }
    }
    else if (mode.equals("Scramble")) {
      cube.scramble();
      cube.printCube();
      cube.printSolve(cube.reverseMoves);
      while (true) {
        System.out.println("Input Move (R, R', L, L', etc.)");
        String move = scn.nextLine();
        cube.mover(move);
        cube.printCube();
        if (move.equals("exit")) {
          cube.printSolve(cube.reverseMoves);
          System.exit(0);
        }
      }
    }

    scn.close();
  }
}
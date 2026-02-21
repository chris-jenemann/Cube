import java.util.ArrayList;
import java.util.Scanner;

public class Cube {
  //make this an array list so we can store lots of moves without worrying about size
  ArrayList < String > reverseMoves = new ArrayList < >();

  //3d array to represent the cube, each face is a 3x3 array of colors
  //face indices: 0=Up(white), 1=Back(blue), 2=Right(red), 3=Front(green), 4=Left(orange), 5=Down(yellow)
  //Im not sure if this will work with the auto grader...
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
      System.out.print("    ");
      for (int j = 0; j < 3; j++) {
        System.out.print(cube[0][i][j]);
      }
      System.out.println();
    }

    //print mid line
    for (int i = 0; i < 3; i++) {
      for (int j = 1; j < 5; j++) {
        for (int k = 0; k < 3; k++) {
          System.out.print(cube[j][i][k]);
        }
        System.out.print(" ");
      }
      System.out.println();
    }

    //print bottom
    for (int i = 0; i < 3; i++) {
      System.out.print("    ");
      for (int j = 0; j < 3; j++) {
        System.out.print(cube[5][i][j]);
      }
      System.out.println();
    }
  }

  //rotates face clockwise
  //Keeps cube consistant with turns
  //thought i didn't need this but broke after multiple moves without it
  //will have to be used 3 times when a face is rotated clockwise
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

    // Back right column (inverted) -> Up left column
    for (int i = 0; i < 3; i++) {
      cube[0][i][0] = cube[1][2 - i][2];
    }

    // Down left column -> Back right column (inverted)
    for (int i = 0; i < 3; i++) {
      cube[1][2 - i][2] = cube[5][i][0];
    }

    // Front left column -> Down left column
    for (int i = 0; i < 3; i++) {
      cube[5][i][0] = cube[3][i][0];
    }

    // Saved Up left column -> Front left column
    for (int i = 0; i < 3; i++) {
      cube[3][i][0] = temp[i];
    }

    rotateFace(4);
    rotateFace(4);
    rotateFace(4); // 3x CW = 1x CCW (Left face rotates counter-clockwise on an L move)
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
      cube[0][i][2] = cube[3][i][2];
    }

    // Down right column -> Front right column
    for (int i = 0; i < 3; i++) {
      cube[3][i][2] = cube[5][i][2];
    }

    // Back left column (inverted) -> Down right column
    for (int i = 0; i < 3; i++) {
      cube[5][i][2] = cube[1][2 - i][0];
    }

    // Saved Up right column -> Back left column (inverted)
    for (int i = 0; i < 3; i++) {
      cube[1][2 - i][0] = temp[i];
    }

    rotateFace(2); // rotate Right face clockwise
  }
  public void rP() {
    r();
    r();
    r();
  }

//up, red row comes to front
  public void u() {
    String[] temp = cube[3][0].clone(); // save Front top row
    cube[3][0] = cube[2][0].clone(); // Right top  -> Front top
    cube[2][0] = cube[1][0].clone(); // Back top   -> Right top
    cube[1][0] = cube[4][0].clone(); // Left top   -> Back top
    cube[4][0] = temp; // saved Front top -> Left top
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
    String[] temp = cube[3][2].clone(); // save Front bottom row
    cube[3][2] = cube[4][2].clone(); // Left bottom  -> Front bottom
    cube[4][2] = cube[1][2].clone(); // Back bottom  -> Left bottom
    cube[1][2] = cube[2][2].clone(); // Right bottom -> Back bottom
    cube[2][2] = temp; // saved Front bottom -> Right bottom
    rotateFace(5);
  }
  public void dP() {
    d();
    d();
    d();
  }

//green face rotates
  public void f() {
    // Save all four edges first to avoid overwrite issues
    String[] upBottom = cube[0][2].clone(); //top
    String[] downTop = cube[5][0].clone(); // bottom
    //left
    String[] leftRight = {
      cube[4][0][2],
      cube[4][1][2],
      cube[4][2][2]
    }; 
    //right
    String[] rightLeft = {
      cube[2][0][0],
      cube[2][1][0],
      cube[2][2][0]
    }; 

    // top to right
    for (int i = 0; i < 3; i++) {
      cube[2][i][0] = upBottom[i];
    }

    // left to top
    for (int i = 0; i < 3; i++) {
      cube[0][2][i] = leftRight[2 - i];
    }

    //down to left
    for (int i = 0; i < 3; i++) {
      cube[4][i][2] = downTop[2 - i];
    }

    // right to down
    for (int i = 0; i < 3; i++) {
      cube[5][0][i] = rightLeft[i];
    }

    rotateFace(3); // rotate Front face clockwise
  }
  public void fP() {
    f();
    f();
    f();
  }

//blue face rotates
  public void b() {
    String[] upTop = cube[0][0].clone(); //black
    String[] downBottom = cube[5][2].clone(); //yellow
    //orange
    String[] leftLeft = {
      cube[4][0][0],
      cube[4][1][0],
      cube[4][2][0]
    }; 
    //red
    String[] rightRight = {
      cube[2][0][2],
      cube[2][1][2],
      cube[2][2][2]
    }; 

    //blacl to orange
    for (int i = 0; i < 3; i++) {
      cube[4][i][0] = upTop[2 - i];
    }

    // red to black
    for (int i = 0; i < 3; i++) {
      cube[0][0][i] = rightRight[2 - i];
    }

    //yellow to red 
    for (int i = 0; i < 3; i++) {
      cube[2][i][2] = downBottom[2 - i];
    }

    //orange yellow
    for (int i = 0; i < 3; i++) {
      cube[5][2][i] = leftLeft[2 - i];
    }

    rotateFace(1); // rotate Back face clockwise
  }
  public void bP() {
    b();
    b();
    b();
  }

//prints all faces of the cube sequentially
  public void printCube() {
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 3; j++) {
        for (int k = 0; k < 3; k++) {
          System.out.print(cube[i][j][k] + " ");
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

  //this is trying to get the 3d cube to work but but its fried
  //i'll come to office hours to try and figure it out 
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
        if (move.equals("exit")) {
          cube.printCube();
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
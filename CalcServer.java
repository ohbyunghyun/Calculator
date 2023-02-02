package Cal;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CalcServer {

  public static void main(String[] args) throws Exception {
    Scanner keyscan = new Scanner(System.in);
    System.out.println("서버 실행 중...");

    ServerSocket serverSocket = new ServerSocket(8888);

    Socket socket = serverSocket.accept();
    System.out.println("클라이언트와 연결됨!");

    Scanner in = new Scanner(socket.getInputStream());
    PrintStream out = new PrintStream(socket.getOutputStream());

    while(true) {
      int result = 0;
      int count = 0;
      int arrcnt = 0;
      String value = in.nextLine();
      if (value.equals("quit")) {
        break;
      }
      String[] str = value.split(" ");

      // left 배열의 크기를 정하기위해 *, / 개수세기
      for (int i = 0; i < str.length - 1; i++) {
        if (str[i].equals("*") || str[i].equals("/")) {
          arrcnt++;
        }
      }
      // left 배열 만들기
      String[] left = new String[str.length - (arrcnt * 2)];

      // 곱셈, 나눗셈 먼저 계산하고 left 배열에 넣기
      for (int i = 0; i < str.length; i++) {
        if (i % 2 != 0) {
          if (str[i].equals("*")) {
            count--;
            left[count++] = (Integer.toString((Integer.parseInt(str[i-1])) * (Integer.parseInt(str[i+1]))));
            i++;
          } else if (str[i].equals("/")) {
            count--;
            left[count++] = (Integer.toString((Integer.parseInt(str[i-1])) / (Integer.parseInt(str[i+1]))));
            i++;
          } else {
            left[count++] = str[i];
          } 
        } else {
          left[count++] = str[i];
        }
      }

      // 남은 값들 중에서 0번과 2번 값 덧뺄셈 하기
      if (left[1].equals("+")) {
        result = Integer.parseInt(left[0]) + Integer.parseInt(left[2]);
      } else if (left[1].equals("-")) {
        result = Integer.parseInt(left[0]) - Integer.parseInt(left[2]);
      }

      // 나머지 값들 덧뺄셈 하기
      for (int i = 3; i < left.length - 1; i++) {
        if (i % 2 != 0) {
          if (left[i].equals("+")) {
            result += Integer.parseInt(left[i+1]);
          } else if (left[i].equals("-")) {
            result -= Integer.parseInt(left[i+1]);
          } else {
            out.println("이상한 값");
          }
        }
      }
      out.println(result);
    }

    socket.close();
    serverSocket.close();

    System.out.println("서버 종료!");
    keyscan.close();
  }

}
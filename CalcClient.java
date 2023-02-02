package Cal;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CalcClient {

  public static void main(String[] args) throws Exception{
    Scanner keyScan = new Scanner(System.in);

    System.out.println("클라이언트 실행 중...");

    Socket socket = new Socket("127.0.0.1", 8888);

    Scanner in = new Scanner(socket.getInputStream());
    PrintStream out = new PrintStream(socket.getOutputStream());
    System.out.println("=========== 계산기 ===========");
    System.out.println("∬  ★주의★ 문자간 띄어쓰기좀  ∬");
    System.out.println("∬  예시 : 5 * 3 + 4 * 4 - 1  ∬");
    System.out.println("∬  나가기 : quit             ∬");

    while(true) {

      System.out.println("==== ↙ 식을 입력하시오 ↙ =====");
      String value = keyScan.nextLine();
      out.println(value);
      if (value.equals("quit")) {
        break;
      }
      int result = in.nextInt();
      System.out.printf("        결과값 : %d\n", result);
    }

    out.close();
    in.close();
    socket.close();

    System.out.println("====    종료    =====");
    keyScan.close();
  }

}
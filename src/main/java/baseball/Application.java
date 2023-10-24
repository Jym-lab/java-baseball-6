package baseball;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Application {
    static final String START = "숫자 야구 게임을 시작합니다.";
    static final String INPUT = "숫자를 입력해주세요 : ";
    static final String END = "3개의 숫자를 모두 맞히셨습니다! 게임 종료";
    static final String RESTART = "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.";

    public static void main(String[] args) {
        boolean isGameOver = true;
        System.out.println(START);
        try {
            while (isGameOver) {
                Game game = new Game(new NumberGenerator());
                isGameOver = game.play();
            }
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }

    }
}

class Game{
    private NumberGenerator numberGenerator;

    public Game(NumberGenerator numberGenerator){
        this.numberGenerator = numberGenerator;
    }

    private boolean restart(String input) {
        int inputNum;
        if (!Player.isNumric(input)) {
            throw new IllegalArgumentException();
        } else {
            inputNum = Integer.parseInt(input);
            if (inputNum != 1 && inputNum != 2) {
                throw new IllegalArgumentException();
            }
        }
        return inputNum == 1;
    }

    private boolean guess(List<Integer>answer, List<Integer>input){
        int strike = 0;
        int ball = 0;
        for (int i = 0; i < 3; i++){
            if (Objects.equals(answer.get(i), input.get(i))){
                strike++;
            } else if (answer.contains(input.get(i))){
                ball++;
            }
        }
        if (strike == 3){
            System.out.println(strike + "스트라이크");
        } else if (strike == 0 && ball == 0) {
            System.out.println("낫싱");
        } else if (strike == 0) {
            System.out.println(ball + "볼");
        } else if (ball == 0) {
            System.out.println(strike + "스트라이크");
        } else {
            System.out.println(ball + "볼 " + strike + "스트라이크");
        }
        return strike == 3;
    }
    public boolean play(){
        List<Integer> answer = numberGenerator.generate();
        while (true){
            System.out.print(Application.INPUT);
            Player player = new Player(Console.readLine());
            if (guess(answer, player.getNumbers())){
                break;
            }
        }
        System.out.println(Application.END);
        System.out.println(Application.RESTART);
        return restart(Console.readLine());
    }
}


class Player {
    private List<Integer> numbers;

    public Player(String input) {
        this.numbers=parseInput(input);
        if (this.numbers.size() != 3){
            throw new IllegalArgumentException();
        }
    }

    private List<Integer> parseInput(String input){
        String[] split = input.split("");
        List<Integer> numbers = new ArrayList<>();
        for (String number : split){
            if (!isNumric(number)){
                throw new IllegalArgumentException();
            }
            if (numbers.contains(Integer.parseInt(number))) {
                throw new IllegalArgumentException();
            }
            numbers.add(Integer.parseInt(number));
        }
        return numbers;
    }
    public static boolean isNumric(String num){
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException error){
            return false;
        }
    }
    public List<Integer> getNumbers(){
        return numbers;
    }
}

class NumberGenerator {
    public List<Integer> generate(){
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < 3){
            int number= Randoms.pickNumberInRange(1, 9);
            if (!numbers.contains(number)){
                numbers.add(number);
            }
        }
        return numbers;
    }
}
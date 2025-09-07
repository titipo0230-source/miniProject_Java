package com.mini.view;

import java.util.List;
import java.util.Scanner;

import com.mini.model.Religion;

public class GameView {
    private Scanner scanner = new Scanner(System.in);

    public String getCountryInput() {
        System.out.print("국가를 입력하세요 (한국 / 일본 / 중국 / 미국) 또는 '종료' 입력 시 종료: ");
        return scanner.nextLine();
    }
// 전파 사실
    public void showSpreadResult(String countryName, String religionName,
                                 String methodName, int gainedScore, int totalScore) {
        System.out.println(countryName + "에 " + religionName +
                           "가 전파되었습니다. (방식: " + methodName + ") → +" +
                           gainedScore + "점 (누적: " + totalScore + ")");
    }
// 종교 점수
    public void showReligionScores(List<Religion> religions) {
        System.out.println("[현재 종교별 점수 현황]");
        for (Religion r : religions) {
            System.out.println("- " + r.getName() + ": " + r.getScore() + "점");
        }
    }
 // 턴 수  
    public void showTurnInfo(int turnCount) {
        System.out.println("\n===== " + turnCount + "턴 시작 =====");
    }
}

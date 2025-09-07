package com.mini.view;

import java.util.List;
import java.util.Scanner;

import com.mini.model.Religion;

public class GameView {
    private Scanner scanner = new Scanner(System.in);

    public String getReligionInput() {
        System.out.println("종교를 입력하세요 (유교 / 불교 / 기독교 / 이슬람) 또는 'exit' 입력 시 종료: ");
        return scanner.nextLine();
    }

    public void showSpreadResult(String countryName, String religionName,
                                 String methodName, int gainedScore, int totalScore) {
        System.out.println(countryName + "에 " + religionName +
                           "가 전파되었습니다. (방식: " + methodName + ") → +" +
                           gainedScore + "점 (누적: " + totalScore + ")");
    }

    public void showReligionScores(List<Religion> religions) {
        System.out.println("[현재 종교별 점수 현황]");
        for (Religion r : religions) {
            System.out.println("- " + r.getName() + ": " + r.getScore() + "점");
        }
    }
}

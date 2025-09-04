package com.mini.view;

import java.util.List;
import java.util.Scanner;


import com.mini.model.Religion;

public class GameView {
	
    private Scanner scanner = new Scanner(System.in);

    public String getSpreadMethodInput() {
        System.out.println("전파 방식을 입력하세요 (선교자 / 수도승 / 이단심문관) 또는 'exit' 입력 시 종료: ");
        return scanner.nextLine();
    }

    public void showSpreadResult(String countryName, String religionName, String methodName, int gainedScore, int totalScore) {
        System.out.println(countryName + "에게 " + religionName + "를 전파합니다. (방식: " + methodName + ") → +" + gainedScore + "점 (누적: " + totalScore + ")");
    }

    public void showReligionScores(List<Religion> religions) {
        System.out.println("[현재 종교별 점수 현황]");
        for (Religion r : religions) {
            System.out.println("- " + r.getName() + ": " + r.getScore() + "점");
        }
    }
}

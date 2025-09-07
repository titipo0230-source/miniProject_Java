package com.mini.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.mini.model.Country;
import com.mini.model.Religion;
import com.mini.model.SpreadMethod;
import com.mini.view.GameView;

public class GameController {
    private List<Country> countries = new ArrayList<>();
    private List<Religion> religions = new ArrayList<>();
    private Map<String, SpreadMethod> spreadMethods = new HashMap<>();
    private GameView view = new GameView();
    private Random random = new Random();

    public GameController() {
        Religion confucianism = new Religion("유교");
        Religion buddhism = new Religion("불교");
        Religion christianity = new Religion("기독교");
        Religion islam = new Religion("이슬람");

        religions.add(confucianism);
        religions.add(buddhism);
        religions.add(christianity);
        religions.add(islam);

        countries.add(new Country("한국"));
        countries.add(new Country("일본"));
        countries.add(new Country("중국"));
        countries.add(new Country("미국"));

        spreadMethods.put("선교자", new SpreadMethod("선교자", 5, 0));
        spreadMethods.put("수도승", new SpreadMethod("수도승", 3, 0));
        spreadMethods.put("이단심문관", new SpreadMethod("이단심문관", 3, -3));
    }

    public void startWar() {
        while (true) {
            // 사용자 입력: 종교 선택
            String religionInput = view.getReligionInput();

            if (religionInput.equalsIgnoreCase("exit")) {
                System.out.println("게임을 종료합니다.");
                break;
            }

            Religion chosenReligion = null;
            for (Religion r : religions) {
                if (r.getName().equalsIgnoreCase(religionInput)) {
                    chosenReligion = r;
                    break;
                }
            }

            if (chosenReligion == null) {
                System.out.println(" 올바르지 않은 종교 이름입니다, 다시 입력해주세요.");
                continue;
            }

            // 랜덤 가져와서 국가랑 전파 방식 
            Country selectedCountry = countries.get(random.nextInt(countries.size()));
            List<SpreadMethod> methods = new ArrayList<>(spreadMethods.values());
            SpreadMethod method = methods.get(random.nextInt(methods.size()));

            // 점수 넣기
            chosenReligion.addScore(method.getGainScore());

            if (method.getLoseScore() != 0) {
                for (Religion r : religions) {
                    if (r != chosenReligion) {
                        r.addScore(method.getLoseScore());
                    }
                }
            }

            // 결과 출력
            view.showSpreadResult(
                selectedCountry.getName(),
                chosenReligion.getName(),
                method.getName(),
                method.getGainScore(),
                chosenReligion.getScore()
            );

            view.showReligionScores(religions);
            System.out.println("------------");

            // 50점 달성 시 우승
            if (chosenReligion.getScore() >= 50) {
                System.out.println("\n🎉 최종 우승 종교: " + chosenReligion.getName() +
                                   " (점수: " + chosenReligion.getScore() + ")");

                saveResultToFile(chosenReligion);
                break;
            }
        }
    }

    //파일 FileWriter BufferedWriter 사용
    private void saveResultToFile(Religion winner) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("result.txt"))) {

            bw.write("=== 종교별 점수표 ===");
            bw.newLine();

            for (Religion r : religions) {
                bw.write(r.getName() + ": " + r.getScore() + "점");
                bw.newLine();
            }

            bw.newLine();
            bw.write("*****최종 우승 종교: " + winner.getName() +
                     " (점수: " + winner.getScore() + ")");
            bw.newLine();

            System.out.println("결과가 파일에 저장 완료됐습니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

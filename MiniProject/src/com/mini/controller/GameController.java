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
	//리스트 생성
    private List<Country> countries = new ArrayList<>();
    private List<Religion> religions = new ArrayList<>();
    private Map<String, SpreadMethod> spreadMethods = new HashMap<>();
    private GameView gv = new GameView();
    private Random random = new Random();
    
    private int turnCount = 0; 

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

    public void startGame() {
        while (true) {
            turnCount++;
            
            gv.showTurnInfo(turnCount);

            // 사용자 입력: 국가 선택
            String countryInput = gv.getCountryInput();

            if (countryInput.equals("종료")) {
                System.out.println("게임을 종료합니다.");
                break;
            }

            Country chosenCountry = null;
            for (Country c : countries) {
                if (c.getName().equals(countryInput)) {
                    chosenCountry = c;
                    break;
                }
            }

            if (chosenCountry == null) {
                System.out.println("올바르지 않은 국가 이름입니다. 다시 입력해주세요.");
                continue;
            }

            // 랜덤 클래스 이용해서 종교, 전파 방식 정해지기
            Religion chosenReligion = religions.get(random.nextInt(religions.size()));
            List<SpreadMethod> methods = new ArrayList<>(spreadMethods.values());
            SpreadMethod method = methods.get(random.nextInt(methods.size()));

            // 점수 반영
            chosenReligion.addScore(method.getGainScore());

            if (method.getLoseScore() != 0) {
                for (Religion r : religions) {
                    if (r != chosenReligion) {
                        r.addScore(method.getLoseScore());
                    }
                }
            }

            // 결과 출력
            gv.showSpreadResult(
                chosenCountry.getName(),
                chosenReligion.getName(),
                method.getName(),
                method.getGainScore(),
                chosenReligion.getScore()
            );

            gv.showReligionScores(religions);
            System.out.println("------------");

            // 50점 달성 시 우승
            if (chosenReligion.getScore() >= 50) {
                System.out.println("****최종 우승 종교: " + chosenReligion.getName() +
                                   " (점수: " + chosenReligion.getScore() + ")");
                saveResultToFile(chosenReligion);
                break;
            }

            // 20턴 넘으면 가장 낮은 종교 제거 + 이단심문관 강화
            if (turnCount > 20 && religions.size()==4) {
                eliminateLowestReligion();
                strengthenInquisitor();
                
            }
        }
    }

    //파일 저장 fileWriter, BufferedWriter, try-resource 
    private void saveResultToFile(Religion winner) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("result.txt"))) {
            bw.write("=== 종교별 점수표 ===");
            bw.newLine();

            for (Religion r : religions) {
                bw.write(r.getName() + ": " + r.getScore() + "점");
                bw.newLine();
            }

            bw.newLine();
            bw.write("최종 우승 종교: " + winner.getName() +
                     " (점수: " + winner.getScore() + ")");
            bw.newLine();

            System.out.println("결과가 파일에 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 가장 낮은 점수의 종교 제거
    private void eliminateLowestReligion() {

        Religion min = religions.get(0);
        for (Religion r : religions) {
            if (r.getScore() <= min.getScore()) {
                min = r;
            }
        }

        System.out.println( min.getName() + " 종교가 가장 낮은 점수이기 때문에 사라집니다.");
        religions.remove(min);
    }

    // 이단심문관 효과 수정
    private void strengthenInquisitor() {
        spreadMethods.put("이단심문관", new SpreadMethod("이단심문관", 5, -5));
        System.out.println("이단심문관 수정 완료");
    }
}

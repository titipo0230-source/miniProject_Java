package com.mini.controller;

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

        // 국가와 종교 매핑 (간단히 종교 1:1 매칭)
        countries.add(new Country("한국", confucianism));
        countries.add(new Country("일본", buddhism));
        countries.add(new Country("중국", confucianism)); // 유교 2개국
        countries.add(new Country("미국", christianity));
        // 전파 방식 정의 (gainScore, loseScore)
        spreadMethods.put("선교자", new SpreadMethod("선교자", 5, 0));
        spreadMethods.put("수도승", new SpreadMethod("수도승", 3, 0));
        spreadMethods.put("이단심문관", new SpreadMethod("이단심문관", 3, -3));
    }
    	
    public void startWar() {
 
        while (true) {
            Country selectedCountry = countries.get(random.nextInt(countries.size()));
            Religion targetReligion = selectedCountry.getReligion();

            String methodInput = view.getSpreadMethodInput();

            if (methodInput.equalsIgnoreCase("exit")) {
                System.out.println("게임을 종료합니다.");
                break;
            }

            SpreadMethod method = spreadMethods.get(methodInput);
            if (method == null) {
                System.out.println("❌ 올바르지 않은 전파 방식입니다. 다시 입력해주세요.");
                continue;
            }

            // 본 종교 점수 증가
            targetReligion.addScore(method.getGainScore());

            // "이단심문관"일 경우만 다른 종교 점수 감소
            if (method.getLoseScore() != 0) {
                for (Religion r : religions) {
                    if (!r.equals(targetReligion)) {
                        r.addScore(method.getLoseScore()); // -3점
                    }
                }
            }
            
            
            view.showSpreadResult(
                selectedCountry.getName(),
                targetReligion.getName(),
                method.getName(),
                method.getGainScore(),
                targetReligion.getScore()
            );

            view.showReligionScores(religions);
            System.out.println("------------");
            
            // 50점 달성 시 우승
            if (targetReligion.getScore() >= 50) {
                System.out.println("************* 최종 우승 종교: ************** " + targetReligion.getName() + " (점수: " + targetReligion.getScore() + ")");
                break;
            }
            
        }
    }
}

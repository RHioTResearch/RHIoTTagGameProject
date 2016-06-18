package org.jboss.rhiot.game;

import com.eurotech.cloud.message.EdcPayload;
import org.jboss.rhiot.ble.bluez.RHIoTTag;
import org.jboss.rhiot.services.api.IRHIoTTagScanner;
import org.jboss.rhiot.services.fsm.GameStateMachine;

/**
 * Simple checks that the CodeSourceTODOs have been changed from their default starting values
 */
public class ValidateTODOs {
    public static void validateStep1() {
        if(CodeSourceTODOs.MY_TAG_NO == -1)
            throw new IllegalStateException("CodeSourceTODOs.MY_TAG_NO has not been configured");
    }

    public static void validateStep2() {
        if(CodeSourceTODOs.MY_GW_NO == -1)
            throw new IllegalStateException("CodeSourceTODOs.MY_GW_NO has not been configured");
    }

    public static void validateStep3() {
        if(CodeSourceTODOs.MY_TAG_ADDRESS.contains("X"))
            throw new IllegalStateException("CodeSourceTODOs.MY_TAG_ADDRESS has not been configured correctly");
    }

    public static void validateStep4() {
        String myAddress = CodeSourceTODOs.MY_TAG_ADDRESS;
        String topic = CodeSourceTODOs.getSubscriptionTopic("");
        String suffix = "/data/"+myAddress;
        if(!topic.endsWith(suffix))
            throw new IllegalStateException("CodeSourceTODOs.getSubscriptionTopic should end with: "+suffix);
    }

    /**
     *
     */
    public static void validateStep5() {
        EdcPayload test = new EdcPayload();
        test.addMetric(IRHIoTTagScanner.TAG_TEMP, 100f);
        test.addMetric(IRHIoTTagScanner.TAG_KEYS, RHIoTTag.KeyState.LEFT.ordinal());
        int keys = CodeSourceTODOs.extractKeyState(test);
        if(keys != RHIoTTag.KeyState.LEFT.ordinal())
            throw new IllegalStateException("CodeSourceTODOs.extractKeyState has not been implemented correctly");
        test.addMetric(IRHIoTTagScanner.TAG_LUX, 43210);
        int lux = CodeSourceTODOs.extractLuxReading(test);
        if(lux != 43210)
            throw new IllegalStateException("CodeSourceTODOs.extractLuxReading has not been implemented correctly");
    }

    /**
     * @see CodeSourceTODOs#extractEvent(EdcPayload)
     * @see CodeSourceTODOs#extractPrevState(EdcPayload)
     * @see CodeSourceTODOs#extractState(EdcPayload)
     */
    public static void validateStep6() {
        EdcPayload test = new EdcPayload();
        test.addMetric(IRHIoTTagScanner.TAG_NEW_STATE, GameStateMachine.GameState.GAMEOVER.name());
        test.addMetric(IRHIoTTagScanner.TAG_PREV_STATE, GameStateMachine.GameState.SHOOTING.name());
        test.addMetric(IRHIoTTagScanner.TAG_EVENT, GameStateMachine.GameEvent.GAME_TIMEOUT.name());
        try {
            GameStateMachine.GameState prevState = CodeSourceTODOs.extractPrevState(test);
            if(prevState != GameStateMachine.GameState.SHOOTING)
                throw new IllegalStateException("CodeSourceTODOs.extractPrevState has not been implemented correctly");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("CodeSourceTODOs.extractPrevState has not been implemented correctly");
        }
        try {
            GameStateMachine.GameState newState = CodeSourceTODOs.extractState(test);
            if(newState != GameStateMachine.GameState.GAMEOVER)
                throw new IllegalStateException("CodeSourceTODOs.extractState has not been implemented correctly");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("CodeSourceTODOs.extractState has not been implemented correctly");
        }
        try {
            GameStateMachine.GameEvent event = CodeSourceTODOs.extractEvent(test);
            if(event != GameStateMachine.GameEvent.GAME_TIMEOUT)
                throw new IllegalStateException("CodeSourceTODOs.extractEvent has not been implemented correctly");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("CodeSourceTODOs.extractEvent has not been implemented correctly");
        }
    }

    /**
     * @see CodeSourceTODOs#extractGameScore(EdcPayload)
     * @see CodeSourceTODOs#extractGameTimeLeft(EdcPayload)
     * @see CodeSourceTODOs#extractShootingWindowTimeLeft(EdcPayload)
     * @see CodeSourceTODOs#extractShotsLeft(EdcPayload)
     */
    public static void validateStep7() {
        EdcPayload test = new EdcPayload();
        test.addMetric(IRHIoTTagScanner.TAG_GAME_TIME_LEFT, 600);
        test.addMetric(IRHIoTTagScanner.TAG_GAME_SCORE, 2500);
        test.addMetric(IRHIoTTagScanner.TAG_SHOOTING_TIME_LEFT, 45);
        test.addMetric(IRHIoTTagScanner.TAG_SHOTS_LEFT, 6);
        int timeLeft = CodeSourceTODOs.extractGameTimeLeft(test);
        if(timeLeft != 600)
            throw new IllegalStateException("CodeSourceTODOs.extractGameTimeLeft has not been implemented correctly");
        int score = CodeSourceTODOs.extractGameScore(test);
        if(score != 2500)
            throw new IllegalStateException("CodeSourceTODOs.extractGameScore has not been implemented correctly");
        timeLeft = CodeSourceTODOs.extractShootingWindowTimeLeft(test);
        if(timeLeft != 45)
            throw new IllegalStateException("CodeSourceTODOs.extractShootingWindowTimeLeft has not been implemented correctly");
        int shots = CodeSourceTODOs.extractShotsLeft(test);
        if(shots != 6)
            throw new IllegalStateException("CodeSourceTODOs.extractShotsLeft has not been implemented correctly");
    }

    /**
     * @see CodeSourceTODOs#extractHitDistance(EdcPayload)
     * @see CodeSourceTODOs#extractHitScore(EdcPayload)
     */
    public static void validateStep8() {
        EdcPayload test = new EdcPayload();
        test.addMetric(IRHIoTTagScanner.TAG_HIT_SCORE, 900);
        test.addMetric(IRHIoTTagScanner.TAG_HIT_RINGS_OFF_CENTER, 4);
        int score = CodeSourceTODOs.extractHitScore(test);
        if(score != 900)
            throw new IllegalStateException("CodeSourceTODOs.extractHitScore has not been implemented correctly");
        int dist = CodeSourceTODOs.extractHitDistance(test);
        if(dist != 4)
            throw new IllegalStateException("CodeSourceTODOs.extractHitDistance has not been implemented correctly");
    }
}

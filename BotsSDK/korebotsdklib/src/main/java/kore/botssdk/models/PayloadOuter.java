package kore.botssdk.models;

/**
 * Created by Ramachandra Pradeep on 12/15/2016.
 */
public class PayloadOuter {

    private String type;
    private PayloadInner payload;
    private String text;
    private String speech_hint;

    public String getType() {
        return type;
    }

    public PayloadInner getPayload() {
        return payload;
    }

    public String getText() {
        return text;
    }

    public String getSpeech_hint() {
        return speech_hint;
    }

    public void setSpeech_hint(String speech_hint) {
        this.speech_hint = speech_hint;
    }
}
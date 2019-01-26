package com.example.illuminate_me;

import android.content.ClipData;

import android.content.ClipData;
//import android.support.v7.app.ActionBarActivity;
import android.content.res.Resources;
import android.icu.text.UnicodeSetIterator;
import android.view.View;
//import com.microsoft.speech.tts.Synthesizer;
//import com.microsoft.speech.tts.Voice;


public class ImageDescription {
    private Synthesizer m_syn;

    private String description;
    public String translatedDescription = "i'm fatimah sheikho";
  //  private Clip  verbalDescription;
public void speech(){
    if (m_syn == null) {
        // Create Text To Speech Synthesizer.
        m_syn = new Synthesizer(Resources.getSystem().getString(R.string.api_key));
    }
    m_syn.SetServiceStrategy(Synthesizer.ServiceStrategy.AlwaysService); //which means that the service needs wifi and always on

    // Use a string for speech. for our app we can send the "Description" as the prameter directly. no need for XML value
    Voice v = new Voice("en-US", "Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)", Voice.Gender.Female, true);
    m_syn.SetVoice(v, null);
    m_syn.SpeakToAudio(translatedDescription);

    // Use SSML for speech.
    // here we change the lang to arabic
    String text = "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xml:lang=\"en-US\"><voice xml:lang=\"en-US\" name=\"Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)\">You can also use SSML markup for text to speech.</voice></speak>";
    m_syn.SpeakSSMLToAudio(text);

}
}

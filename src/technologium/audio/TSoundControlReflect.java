package technologium.audio;

import mindustry.audio.SoundControl;
import java.lang.reflect.*;

import arc.audio.Music;
import arc.util.Timer;

import static mindustry.Vars.*;

public class TSoundControlReflect {
    private boolean available = false;

    public Music current;
    public Field currentField;
    public float fade;
    public Field fadeField;
    public boolean silenced;
    public Field silencedField;

    public Method playMethod;
    public Method playOnceMethod;
    public Method silenceMethod;

    public TSoundControlReflect() {
        checkAvailable();
        //just to make sure it's available
        Timer.schedule(this::checkAvailable, 3);
    }

    public boolean checkAvailable() {
        try {
            currentField = SoundControl.class.getDeclaredField("current");
            currentField.setAccessible(true);
            fadeField = SoundControl.class.getDeclaredField("fade");
            fadeField.setAccessible(true);
            silencedField = SoundControl.class.getDeclaredField("silenced");
            silencedField.setAccessible(true);

            playMethod = SoundControl.class.getDeclaredMethod("play", Music.class);
            playMethod.setAccessible(true);
            playOnceMethod = SoundControl.class.getDeclaredMethod("playOnce", Music.class);
            playOnceMethod.setAccessible(true);
            silenceMethod = SoundControl.class.getDeclaredMethod("silence");
            silenceMethod.setAccessible(true);

            return available = true;
        }
        catch(Exception ignored) {
            return available = false;
        }
    }

    public void update() {
        if(!available) return;
        try {
            current = (Music)currentField.get(control.sound);
            fade = (float)currentField.get(control.sound);
            silenced = (boolean)currentField.get(control.sound);
        }
        catch(Exception ignored) {}
    }

    public void play(Music music) {
        try {
            if(available) playMethod.invoke(control.sound, music);
        }
        catch (Exception ignored) {}
    }

    public void playOnce(Music music) {
        try {
            if(available) playOnceMethod.invoke(control.sound, music);
        }
        catch (Exception ignored) {}
    }

    public void silence() {
        try {
            if(available) silenceMethod.invoke(control.sound);
        }
        catch (Exception ignored) {}
    }
}